package com.example.lit_fits_application.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.PublicKeyClientInterface;
import com.example.lit_fits_application.clients.UserClientInterface;
import com.example.lit_fits_application.entities.BodyPart;
import com.example.lit_fits_application.entities.Color;
import com.example.lit_fits_application.entities.Company;
import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.GarmentType;
import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.Mood;
import com.example.lit_fits_application.entities.User;
import com.example.lit_fits_application.entities.UserType;
import com.example.lit_fits_application.miscellaneous.Encryptor;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for the registration of new Users
 *
 * @author Carlos Mendez
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    /**
     * Button to submit the data entered
     */
    private Button btnSubmit;
    /**
     * Button to cancel the registration
     */
    private Button btnCancel;
    /**
     * Field to enter the username
     */
    private EditText fieldUsername;
    /**
     * Field to enter the password
     */
    private EditText fieldPassword;
    /**
     * Field to enter the password again to confirm
     */
    private EditText fieldConfirmPassword;
    /**
     * Field to enter the email
     */
    private EditText fieldEmail;
    /**
     * Field to enter the full name
     */
    private EditText fieldFullName;
    /**
     * ArrayList of Fields, to check if they're filled
     */
    private ArrayList<EditText> textFields;
    /**
     * User registering
     */
    private User user;
    /**
     * Address of the server
     */
    // private String uri;
    /**
     * Bytes of the public key
     */
    // private byte[] publicKeyBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        setListeners();
        // uri = getResources().getString(R.string.uri);
        textFields = new ArrayList<>();
        addFieldsToArray();
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Explode());
        // getPublicKey();
    }

    /**
     * Sets the Listeners for the elements of the View
     */
    private void setListeners() {
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    /**
     * Assigns the Views
     */
    private void findViews() {
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        fieldUsername = findViewById(R.id.fieldUsername);
        fieldPassword = findViewById(R.id.fieldPassword);
        fieldConfirmPassword = findViewById(R.id.fieldConfirmPassword);
        fieldEmail = findViewById(R.id.fieldEmail);
        fieldFullName = findViewById(R.id.fieldFullName);
    }

    /**
     * Adds the fields to an array to check later if they're filled
     */
    private void addFieldsToArray() {
        textFields.add(fieldUsername);
        textFields.add(fieldFullName);
        textFields.add(fieldConfirmPassword);
        textFields.add(fieldEmail);
        textFields.add(fieldPassword);
    }

    /**
     * This method dictates what happens when the buttons are pressed
     *
     * @param v The View element from which the event was called
     */
    public void onClick(View v) {
        MediaPlayer.create(this, R.raw.oof);
        try {
            if (v.getId() == btnSubmit.getId()) {
                boolean filledFields = true;
                for (EditText field : textFields) {
                    if (field.getText().length() == 0) {
                        filledFields = false;
                        break;
                    }
                }
                if (fieldPassword.getText().toString().trim().equals(fieldConfirmPassword.getText().toString().trim()) && filledFields) {
                    setUserData();
                    // registerUser();
                    openMainMenu(user);
                } else if (!fieldPassword.getText().toString().trim().equals(fieldConfirmPassword.getText().toString().trim())) {
                    createAlertDialog("Passwords don't match");
                    fieldConfirmPassword.requestFocus();
                } else if (!filledFields) {
                    createAlertDialog("There are empty fields");
                    // Finds the first empty field and focuses it
                    Optional<EditText> optional = textFields.stream().filter(editText -> editText.getText().equals("")).findFirst();
                    optional.get().requestFocus();
                }
            } else if (v.getId() == btnCancel.getId()) {
                Intent loginActivityIntent = new Intent(this, LoginActivity.class);
                startActivity(loginActivityIntent);
            }
        } catch (Exception e) {
            createAlertDialog(e.getMessage());
        }
    }

    /**
     * Attempts to register the user
     */
    /*
        private void registerUser() {
            UserClientInterface userClientInterface = new ClientFactory().getUserClient(uri);
            Call<Void> call = userClientInterface.createUser(user);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        openMainMenu(user);
                    } else if (response.code() == 500) {
                        createAlertDialog("Unknown Error");
                    } else {
                        createAlertDialog(String.valueOf(response.code()) + " Error");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    createAlertDialog(t.getMessage());
                }
            });
        }
     */

    /**
     * Attempts to get the public key from the server
     */
    /*
        private void getPublicKey() {
            PublicKeyClientInterface publicKeyClientInterface = new ClientFactory().getPublicKeyClient(uri);
            Call<ResponseBody> publicKeyCall = publicKeyClientInterface.getPublicKey();
            publicKeyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> responseBodyCall, Response<ResponseBody> response) {
                    assignPublicKeyBytes(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> response, Throwable t) {
                    createAlertDialog(t.getMessage());
                }
            });
        }
     */
    /*
        private void assignPublicKeyBytes(Response<ResponseBody> response) {
            try {
                InputStream publicKeyByteInputStream = response.body().byteStream();
                publicKeyBytes = IOUtils.toByteArray(publicKeyByteInputStream);
            } catch (IOException e) {
                createAlertDialog("Server Error");
            }
        }
    */

    /**
     * Creates an AlertDialog
     *
     * @param message
     */
    private void createAlertDialog(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.show();
    }

    /**
     * Creates and sets the data of the user to be sent to the server
     */
    private void setUserData() throws Exception {
        user = new User();
        user.setEmail(String.valueOf(fieldEmail.getText()));
        user.setFullName(String.valueOf(fieldFullName.getText()));
        user.setUsername(String.valueOf(fieldUsername.getText()));
        // String encryptedPassword = Encryptor.encryptText(String.valueOf(fieldPassword.getText()), publicKeyBytes);
        // user.setPassword(String.valueOf(encryptedPassword));
        user.setPassword(String.valueOf(fieldPassword.getText()));
        user.setType(UserType.USER);
        user.addLikedColor(new Color("Black"));
        user.addLikedMaterial(new Material("Leather"));
        List<Garment> auxCompanyGarments = new ArrayList<>();
        Company auxCompany = new Company("A1111111A", "abcd", "Some Company", "111223344", "some@com.pany", new Date(), new Date(), auxCompanyGarments);
        Set<Garment> auxUserGarments = new HashSet<>();
        createAndAddGarments(auxCompanyGarments, auxCompany, auxUserGarments);
        user.setGarments(auxUserGarments);
    }

    private void createAndAddGarments(List<Garment> auxCompanyGarments, Company auxCompany, Set<Garment> auxUserGarments) {
        Garment auxGarment1 = createAuxGarment1(auxCompany);
        Garment auxGarment2 = createAuxGarment2(auxCompany);
        Garment auxGarment3 = createAuxGarment3(auxCompany);
        Garment auxGarment4 = createAuxGarment4(auxCompany);
        Garment auxGarment5 = createAuxGarment5(auxCompany);
        Garment auxGarment6 = createAuxGarment6(auxCompany);
        auxCompanyGarments.add(auxGarment1);
        auxCompanyGarments.add(auxGarment2);
        auxCompanyGarments.add(auxGarment3);
        auxCompanyGarments.add(auxGarment4);
        auxCompanyGarments.add(auxGarment5);
        auxCompanyGarments.add(auxGarment6);
        auxCompany.setGarments(auxCompanyGarments);
        auxUserGarments.add(auxGarment1);
        auxUserGarments.add(auxGarment2);
        auxUserGarments.add(auxGarment3);
        auxUserGarments.add(auxGarment4);
    }

    private Garment createAuxGarment6(Company auxCompany) {
        Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.auxsix);
        Set<Color> auxColors1 = new HashSet<>();
        Color auxColor1 = new Color("Green");
        Color auxColor2 = new Color("Yellow");
        auxColors1.add(auxColor1);
        auxColors1.add(auxColor2);
        Set<Material> auxMaterials1 = new HashSet<>();
        Material auxMaterial1 = new Material("Leather");
        Material auxMaterial2 = new Material("Silk");
        auxMaterials1.add(auxMaterial1);
        auxMaterials1.add(auxMaterial2);
        return new Garment("12345", "cute.jpg", "Some Guy", 123.45, Mood.FORMAL, BodyPart.FULLBODY, GarmentType.BEANIE, true, true, false, "", auxCompany, auxColors1, auxMaterials1, picture);
    }

    private Garment createAuxGarment5(Company auxCompany) {
        Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.auxfive);
        Set<Color> auxColors1 = new HashSet<>();
        Color auxColor1 = new Color("Yellow");
        Color auxColor2 = new Color("White");
        auxColors1.add(auxColor1);
        auxColors1.add(auxColor2);
        Set<Material> auxMaterials1 = new HashSet<>();
        Material auxMaterial1 = new Material("Nylon");
        Material auxMaterial2 = new Material("Cotton");
        auxMaterials1.add(auxMaterial1);
        auxMaterials1.add(auxMaterial2);
        return new Garment("12345", "cute.jpg", "Some Guy", 123.45, Mood.FORMAL, BodyPart.OTHER, GarmentType.BEANIE, true, true, false, "", auxCompany, auxColors1, auxMaterials1, picture);
    }

    private Garment createAuxGarment4(Company auxCompany) {
        Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.auxfour);
        Set<Color> auxColors1 = new HashSet<>();
        Color auxColor1 = new Color("Black");
        Color auxColor2 = new Color("White");
        auxColors1.add(auxColor1);
        auxColors1.add(auxColor2);
        Set<Material> auxMaterials1 = new HashSet<>();
        Material auxMaterial1 = new Material("Cotton");
        auxMaterials1.add(auxMaterial1);
        return new Garment("12345", "cute.jpg", "Some Guy", 123.45, Mood.FORMAL, BodyPart.HAT, GarmentType.BEANIE, true, true, false, "", auxCompany, auxColors1, auxMaterials1, picture);
    }

    private Garment createAuxGarment3(Company auxCompany) {
        Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.auxthree);
        Set<Color> auxColors1 = new HashSet<>();
        Color auxColor1 = new Color("Black");
        Color auxColor2 = new Color("Red");
        auxColors1.add(auxColor1);
        auxColors1.add(auxColor2);
        Set<Material> auxMaterials1 = new HashSet<>();
        Material auxMaterial1 = new Material("Leather");
        Material auxMaterial2 = new Material("Jean");
        Material auxMaterial3 = new Material("Cotton");
        auxMaterials1.add(auxMaterial1);
        auxMaterials1.add(auxMaterial2);
        auxMaterials1.add(auxMaterial3);
        return new Garment("12345", "cute.jpg", "Some Guy", 123.45, Mood.FORMAL, BodyPart.SHOE, GarmentType.BEANIE, true, true, false, "", auxCompany, auxColors1, auxMaterials1, picture);
    }

    private Garment createAuxGarment2(Company auxCompany) {
        Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.auxtwo);
        Set<Color> auxColors1 = new HashSet<>();
        Color auxColor1 = new Color("Blue");
        Color auxColor2 = new Color("Red");
        auxColors1.add(auxColor1);
        auxColors1.add(auxColor2);
        Set<Material> auxMaterials1 = new HashSet<>();
        Material auxMaterial1 = new Material("Silk");
        Material auxMaterial2 = new Material("Nylon");
        auxMaterials1.add(auxMaterial1);
        auxMaterials1.add(auxMaterial2);
        return new Garment("12345", "cute.jpg", "Some Guy", 123.45, Mood.FORMAL, BodyPart.TOP, GarmentType.BEANIE, true, true, false, "", auxCompany, auxColors1, auxMaterials1, picture);
    }

    private Garment createAuxGarment1(Company auxCompany) {
        Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.auxone);
        Set<Color> auxColors1 = new HashSet<>();
        Color auxColor1 = new Color("Black");
        Color auxColor2 = new Color("White");
        auxColors1.add(auxColor1);
        auxColors1.add(auxColor2);
        Set<Material> auxMaterials1 = new HashSet<>();
        Material auxMaterial1 = new Material("Leather");
        Material auxMaterial2 = new Material("Cotton");
        auxMaterials1.add(auxMaterial1);
        auxMaterials1.add(auxMaterial2);
        return new Garment("12345", "cute.jpg", "Some Guy", 123.45, Mood.FORMAL, BodyPart.BOTTOM, GarmentType.BEANIE, true, true, false, "", auxCompany, auxColors1, auxMaterials1, picture);
    }

    /**
     * Opens the MainMenuActivity after a successful Registration
     *
     * @param user
     */
    private void openMainMenu(@NotNull User user) {
        Intent MainMenuIntent = new Intent(this, MainMenuActivity.class);
        // MainMenuIntent.putExtra("URI", uri);
        MainMenuIntent.putExtra("USER", user);
        startActivity(MainMenuIntent);
    }
}
