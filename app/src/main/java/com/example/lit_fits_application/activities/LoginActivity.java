package com.example.lit_fits_application.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.UserClientInterface;
import com.example.lit_fits_application.entities.User;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText fieldUsername;
    private EditText fieldPassword;
    private Button btnLogin;
    private Button btnRegister;
    private ArrayList<EditText> textFields;
    private User user;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setListeners();
        textFields = new ArrayList<>();
        addFieldsToArray();
        // Read the uri
    }

    /**
     * Sets the Listeners for the elements of the View
     */
    private void setListeners() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
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
     * @param v
     */
    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == btnLogin.getId()) {
                onBtnLoginPress();
            } else if (v.getId() == btnRegister.getId()) {
                onBtnRegisterPress();
            }

        } catch (Exception e) {
            createAlertDialog(e.getMessage());
        }
    }

    /**
     * Sets the data entered for the User object
     */
    private void setUserLoginData() {
        user = new User();
        user.setUsername(String.valueOf(fieldUsername.getText()));
        user.setPassword(String.valueOf(fieldPassword.getText()));
    }

    /**
     * This method will be on charge of doing the login.
     * First it will verify if all the fields are filled, then will try to connect and verify the data
     */
    public void onBtnLoginPress() {
        boolean filledFields = true;
        for (EditText field : textFields) {
            if (field.getText().length() == 0) {
                filledFields = false;
                break;
            }
        }
        if (filledFields) {
            UserClientInterface userClientInterface = new ClientFactory().getUserClient(uri);
            setUserLoginData();
            Call<User> call = userClientInterface.login(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        openMainMenu(response);
                    } else if (response.code() == 404) {
                        createAlertDialog("Not Found");
                    } else if (response.code() == 500) {
                        createAlertDialog("Unknown Error");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    createAlertDialog(t.getMessage());
                }
            });
        } else {
            createAlertDialog("There are empty fields");
        }
    }

    /**
     * Opens the MainMenuActivity after a successful login
     *
     * @param response
     */
    private void openMainMenu(@org.jetbrains.annotations.NotNull Response<User> response) {
        Intent MainMenuIntent = new Intent(this, MainMenuActivity.class);
        MainMenuIntent.putExtra("URI", uri);
        MainMenuIntent.putExtra("USER", response.body());
        startActivity(MainMenuIntent);
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
     * Opens the Registration Activity
     */
    public void onBtnRegisterPress() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        registerIntent.putExtra("URI", uri);
        startActivity(registerIntent);
    }
}
