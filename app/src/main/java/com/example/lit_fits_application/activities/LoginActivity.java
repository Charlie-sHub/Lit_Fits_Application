package com.example.lit_fits_application.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.PublicKeyClientInterface;
import com.example.lit_fits_application.clients.UserClientInterface;
import com.example.lit_fits_application.entities.User;
// import com.example.lit_fits_application.miscellaneous.AdminSQLiteOpenHelper;
import com.example.lit_fits_application.miscellaneous.Encryptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    private String uri;
    /**
     * Lit tunes yo!
     */
    private MediaPlayer musicPlayer;
    /**
     * Bytes of the publicKey
     */
    private byte[] publicKeyBytes;

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
        // Setting the transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
    }

    /**
     * Sets the Listeners for the elements of the View
     */
    private void setListeners() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        fieldUsername.setOnFocusChangeListener(this);
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
        String encryptedPassword = Encryptor.encryptText(String.valueOf(fieldPassword.getText()), publicKeyBytes);
        user.setPassword(String.valueOf(encryptedPassword));
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
            getPublicKey();
            setUserLoginData();
            loginUser();
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
    private void loginUser() {
        UserClientInterface userClientInterface = new ClientFactory().getUserClient(uri);
        Call<User> userCall = userClientInterface.login(user);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    // registerUser();
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
            }
        });
    }

    /**
     * Attempts to get the public key from the server
     */
    private void getPublicKey() {
        PublicKeyClientInterface publicKeyClientInterface = new ClientFactory().getPublicKeyClient(uri);
        Call<ResponseBody> publicKeyCall = publicKeyClientInterface.getPublicKey();
        publicKeyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> responseBodyCall, Response<ResponseBody> response) {
                try {
                    publicKeyBytes = response.body().bytes();
                } catch (IOException e) {
                    createAlertDialog("Server Error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> response, Throwable t) {
                createAlertDialog(t.getMessage());
            }
        });
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

    /**
     * Saves the username and password of the User in a database so the app "remembers" the password
     */
    /*
    public void registerUser() {
        // What database file?
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();
        String username = fieldUsername.getText().toString();
        String password = fieldPassword.getText().toString();
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

     */

    /**
     * Looks for the password of the User
     */
    /*
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

     */

    /**
     * Checks when the focus changes and acts accordingly
     *
     * @param view
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (view.getId() == fieldPassword.getId()) {
            // searchUser();
        }
    }
}
