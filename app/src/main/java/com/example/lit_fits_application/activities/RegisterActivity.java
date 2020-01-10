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
import com.example.lit_fits_application.entities.User;
import com.example.lit_fits_application.entities.UserType;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        textFields = new ArrayList<>();

        addFieldsToArray();
    }

    private void findViews() {
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        fieldUsername = findViewById(R.id.fieldUsername);
        fieldPassword = findViewById(R.id.fieldPassword);
        fieldConfirmPassword = findViewById(R.id.fieldConfirmPassword);
        fieldEmail = findViewById(R.id.fieldEmail);
        fieldFullName = findViewById(R.id.fieldFullName);
    }

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
                    createUser();
                    ClientThread client = new ClientThread();
                    client.setAction("SIGNIN");
                    client.setUser(user);
                    client.setAppLogic(appLogic);
                    client.start();
                    try {
                        client.join();
                    } catch (Exception e) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        alert.setMessage(e.getMessage());
                        alert.show();
                    }
                    if (client.getMessage().getContent() instanceof Boolean) {
                        Intent mainMenuActivityIntent = new Intent(this, MainMenuActivity.class);
                        mainMenuActivityIntent.putExtra("USER", user);
                        startActivity(mainMenuActivityIntent);
                    } else {
                        Exception e = (Exception) client.getMessage().getContent();
                        AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        alert.setMessage(e.getMessage());
                        alert.show();
                    }
                } else if (!fieldPassword.getText().equals(fieldConfirmPassword.getText())) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setMessage("Passwords don't match");
                    alert.show();
                } else if (!filledFields) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setMessage("There are empty fields");
                    alert.show();
                }
            } else if (v.getId() == btnCancel.getId()) {
                Intent loginActivityIntent = new Intent(this, LoginActivity.class);
                startActivity(loginActivityIntent);
            }
        } catch (Exception e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(e.getMessage());
            alert.show();
        }
    }

    /**
     * Creates and sets the data of the user to be sent to the server
     */
    private void createUser() {
        user = new User();
        user.setEmail(String.valueOf(fieldEmail.getText()));
        user.setFullName(String.valueOf(fieldFullName.getText()));
        user.setUsername(String.valueOf(fieldUsername.getText()));
        user.setPassword(String.valueOf(fieldPassword.getText()));
        user.setType(UserType.USER);
    }
}
