package com.example.lit_fits_application.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.UserClientInterface;
import com.example.lit_fits_application.entities.User;
import com.example.lit_fits_application.entities.UserType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button btnSubmit;
    private Button btnCancel;
    private EditText fieldUsername;
    private EditText fieldPassword;
    private EditText fieldConfirmPassword;
    private EditText fieldEmail;
    private EditText fieldFullName;
    private ArrayList<EditText> textFields;
    private User user;
    private Bundle extrasBundle;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        setListeners();
        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        uri = extrasBundle.getString("URI");
        textFields = new ArrayList<>();

        addFieldsToArray();
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
                    createUser();
                    Call<User> call = userClientInterface.create(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200) {
                                openMainMenu(response);
                            } else if (response.code() == 500) {
                                createAlertDialog("Unknown Error");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            createAlertDialog(t.getMessage());
                        }
                    });
                } else if (!fieldPassword.getText().equals(fieldConfirmPassword.getText())) {
                    createAlertDialog("Passwords don't match");
                } else if (!filledFields) {
                    createAlertDialog("There are empty fields");
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
    private User createUser() {
        user = new User();
        user.setEmail(String.valueOf(fieldEmail.getText()));
        user.setFullName(String.valueOf(fieldFullName.getText()));
        user.setUsername(String.valueOf(fieldUsername.getText()));
        user.setPassword(String.valueOf(fieldPassword.getText()));
        user.setType(UserType.USER);
        return user;
    }

    /**
     * Opens the MainMenuActivity after a successful Registration
     *
     * @param response
     */
    private void openMainMenu(@org.jetbrains.annotations.NotNull Response<User> response) {
        Intent MainMenuIntent = new Intent(this, MainMenuActivity.class);
        MainMenuIntent.putExtra("URI", uri);
        MainMenuIntent.putExtra("USER", response.body());
        startActivity(MainMenuIntent);
    }
}
