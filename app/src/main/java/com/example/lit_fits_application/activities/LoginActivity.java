package com.example.lit_fits_application.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
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

/**
 * Activity to login, where the app starts
 *
 * @author Carlos Mendez
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
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
    private String uri;
    /**
     * Lit tunes yo!
     */
    private MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        musicPlayer = MediaPlayer.create(LoginActivity.this, R.raw.app_soundtrack);
        musicPlayer.start();
        uri = getResources().getString(R.string.uri);
        findViews();
        setListeners();
        textFields = new ArrayList<>();
        addFieldsToArray();
        // Implement SQLite so the user doesn't need to log in again
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
        startActivity(registerIntent);
    }
}
