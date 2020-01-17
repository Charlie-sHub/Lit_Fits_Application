package com.example.lit_fits_application.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Optional;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.UserClientInterface;
import com.example.lit_fits_application.entities.User;
import com.example.lit_fits_application.entities.UserType;

import org.jetbrains.annotations.NotNull;

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
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        setListeners();
        uri = getResources().getString(R.string.uri);
        textFields = new ArrayList<>();
        addFieldsToArray();
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Explode());
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
        try {
            if (v.getId() == btnSubmit.getId()) {
                boolean filledFields = true;
                for (EditText field : textFields) {
                    if (field.getText().length() == 0) {
                        filledFields = false;
                        break;
                    }
                }
                if (fieldPassword.getText().equals(fieldConfirmPassword.getText()) && filledFields) {
                    UserClientInterface userClientInterface = new ClientFactory().getUserClient(uri);
                    setUserData();
                    Call<Void> call = userClientInterface.createUser(user);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200) {
                                openMainMenu(user);
                            } else if (response.code() == 500) {
                                createAlertDialog("Unknown Error");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            createAlertDialog(t.getMessage());
                        }
                    });
                } else if (!fieldPassword.getText().equals(fieldConfirmPassword.getText())) {
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
    private void setUserData() {
        user = new User();
        user.setEmail(String.valueOf(fieldEmail.getText()));
        user.setFullName(String.valueOf(fieldFullName.getText()));
        user.setUsername(String.valueOf(fieldUsername.getText()));
        user.setPassword(String.valueOf(fieldPassword.getText()));
        user.setType(UserType.USER);
    }

    /**
     * Opens the MainMenuActivity after a successful Registration
     *
     * @param user
     */
    private void openMainMenu(@NotNull User user) {
        Intent MainMenuIntent = new Intent(this, MainMenuActivity.class);
        MainMenuIntent.putExtra("URI", uri);
        MainMenuIntent.putExtra("USER", user);
        startActivity(MainMenuIntent);
    }
}
