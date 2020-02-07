package com.example.lit_fits_application.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.lit_fits_application.miscellaneous.AdminSQLiteOpenHelper;
import com.example.lit_fits_application.miscellaneous.Encryptor;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import androidx.core.content.ContextCompat;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Activity to login, where the app starts
 *
 * @author Carlos Mendez
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    /**
     * Field to enter the username
     */
    private EditText fieldUsername;
    /**
     * Field to enter the password
     */
    private EditText fieldPassword;
    /**
     * Button to attempt login
     */
    private Button btnLogin;
    /**
     * Button to open the registration activity
     */
    private Button btnRegister;
    /**
     * ArrayList of text fields to check if they're filled
     */
    private ArrayList<EditText> textFields;
    /**
     * User to attempt login
     */
    private User user;
    /**
     * Address of the server
     */
    // private String uri;
    /**
     * Lit tunes yo!
     */
    private MediaPlayer musicPlayer;

    /**
     * Bytes of the publicKey
     */
    // private byte[] publicKeyBytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        musicPlayer = MediaPlayer.create(LoginActivity.this, R.raw.gotta_go_fast_soundtrack);
        musicPlayer.start();
        // uri = getResources().getString(R.string.uri);
        findViews();
        animate();
        setListeners();
        textFields = new ArrayList<>();
        addFieldsToArray();
        // Setting the transitions
        getWindow().setExitTransition(new Explode());
        // getPublicKey();
    }

    private void animate() {
        Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        Animation animationZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_back_in);
        btnRegister.startAnimation(animationFadeOut);
        btnLogin.startAnimation(animationZoomIn);
    }

    /**
     * Sets the Listeners for the elements of the View
     */
    private void setListeners() {
        btnLogin.setOnClickListener(this);
        btnLogin.setBackgroundColor(android.graphics.Color.RED);
        btnRegister.setOnClickListener(this);
        btnRegister.setBackgroundColor(android.graphics.Color.BLUE);
        fieldUsername.setOnFocusChangeListener(this);
        fieldPassword.setOnFocusChangeListener(this);
    }

    /**
     * Assigns the Views
     */
    private void findViews() {
        fieldUsername = findViewById(R.id.fieldUsername);
        fieldPassword = findViewById(R.id.fieldPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }

    /**
     * Adds the fields to an array to check later if they're filled
     */
    private void addFieldsToArray() {
        textFields.add(fieldUsername);
        textFields.add(fieldPassword);

    }

    /**
     * Controls the buttons
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        MediaPlayer.create(this, R.raw.oof);
        try {
            if (view.getId() == btnLogin.getId()) {
                onBtnLoginPress();
            } else if (view.getId() == btnRegister.getId()) {
                onBtnRegisterPress();
            }
        } catch (Exception e) {
            createAlertDialog(e.getMessage());
        }
    }

    /**
     * Sets the data entered for the User object
     */
    private void setUserLoginData() throws Exception {
        user = new User();
        user.setUsername(String.valueOf(fieldUsername.getText()));
        // String encryptedPassword = Encryptor.encryptText(String.valueOf(fieldPassword.getText()), publicKeyBytes);
        // user.setPassword(String.valueOf(encryptedPassword));
        user.setPassword(String.valueOf(fieldUsername.getText()));
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
     * This method will be on charge of doing the login.
     * First it will verify if all the fields are filled, then will try to connect and verify the data
     */
    public void onBtnLoginPress() throws Exception {
        boolean filledFields = true;
        for (EditText field : textFields) {
            if (field.getText().length() == 0) {
                filledFields = false;
                break;
            }
        }
        // If the fields are all filled then the login attempt can continue
        if (filledFields) {
            setUserLoginData();
            registerUser(user);
            openMainMenu(user);
            // loginUser();
        } else {
            createAlertDialog("There are empty fields");
            // Finds the first empty field and focuses it
            Optional<EditText> optional = textFields.stream().filter(editText -> editText.getText().equals("")).findFirst();
            optional.get().requestFocus();
        }
    }

    /**
     * Attempts a Login
     */
    /*
        private void loginUser() {
            UserClientInterface userClientInterface = new ClientFactory().getUserClient(uri);
            Call<User> userCall = userClientInterface.login(user);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        registerUser();
                        openMainMenu(response);
                    } else if (response.code() == 404) {
                        createAlertDialog("User not found");
                    } else if (response.code() == 500) {
                        createAlertDialog("Unknown Error");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    createAlertDialog(t.getMessage());
                    t.printStackTrace();
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
     * Opens the MainMenuActivity after a successful login
     *
     * @param user
     */
    private void openMainMenu(User user) {
        Intent MainMenuIntent = new Intent(this, MainMenuActivity.class);
        MainMenuIntent.putExtra("USER", user);
        startActivity(MainMenuIntent);
    }
    /*
        private void openMainMenu(@org.jetbrains.annotations.NotNull Response<User> response) {
            Intent MainMenuIntent = new Intent(this, MainMenuActivity.class);
            MainMenuIntent.putExtra("USER", response.body());
            startActivity(MainMenuIntent);
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
     * Opens the Registration Activity
     */
    public void onBtnRegisterPress() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    /**
     * Saves the username and password of the User in a database so the app "remembers" the password
     */
    public void registerUser(User user) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();
        String username = user.getUsername();
        String password = user.getPassword();
        if (!username.isEmpty() && !password.isEmpty()) {
            ContentValues register = new ContentValues();
            register.put("username", username);
            register.put("password", password);
            database.insert("users", null, register);
            database.close();
        } else {
            Toast.makeText(this, "You must fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Looks for the password of the User
     */
    public void searchUser() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();
        String username = fieldUsername.getText().toString();
        if (!username.isEmpty()) {
            Cursor row = database.rawQuery("select password from users where username =" + username, null);
            if (row.moveToFirst()) {
                fieldPassword.setText(row.getString(0));
                database.close();
            } else {
                Toast.makeText(this, "No username found, first timer huh?", Toast.LENGTH_SHORT).show();
                database.close();
            }
        } else {
            Toast.makeText(this, "You must introduce the username", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks when the focus changes and acts accordingly
     *
     * @param view
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (view.getId() == fieldPassword.getId()) {
            searchUser();
        }
    }
}
