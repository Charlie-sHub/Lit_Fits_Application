package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.entities.User;

/**
 * Activity for the main menu of Lit Fits
 *
 * @author Carlos Mendez
 */
public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Button to go to the recommendations activity
     */
    private Button buttonRecommendation;
    /**
     * Button to go to the tastes activity
     */
    private Button buttonTastes;
    /**
     * Button to go to the closet activity
     */
    private Button buttonCloset;
    /**
     * Button to go to the account modification activity
     */
    private Button buttonModifyAccount;
    /**
     * Buttong to log out of Lit Fits
     */
    private Button buttonLogOut;
    /**
     * The User that logged in
     */
    private User user;
    private Bundle extrasBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findButtons();
        setListenerForButtons();
        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        user = (User) extrasBundle.get("USER");
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Explode());
    }

    private void setListenerForButtons() {
        buttonCloset.setOnClickListener(this);
        buttonRecommendation.setOnClickListener(this);
        buttonTastes.setOnClickListener(this);
        buttonModifyAccount.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);
    }

    private void findButtons() {
        buttonCloset = findViewById(R.id.buttonCloset);
        buttonTastes = findViewById(R.id.buttonTastes);
        buttonRecommendation = findViewById(R.id.buttonRecommendation);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonModifyAccount = findViewById(R.id.buttonModifyAccount);
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == buttonCloset.getId()) {
                Intent closetActivityIntent = new Intent(this, ClosetActivity.class);
                closetActivityIntent.putExtra("USER", user);
                startActivity(closetActivityIntent);
            } else if (v.getId() == buttonTastes.getId()) {
                Intent tastesActivityIntent = new Intent(this, TastesActivity.class);
                tastesActivityIntent.putExtra("USER", user);
                startActivity(tastesActivityIntent);
            } else if (v.getId() == buttonRecommendation.getId()) {
                Intent recommendationActivityIntent = new Intent(this, RecommendationActivity.class);
                recommendationActivityIntent.putExtra("USER", user);
                startActivity(recommendationActivityIntent);
            } else if (v.getId() == buttonModifyAccount.getId()) {
                Intent modifyAccountActivityIntent = new Intent(this, ModifyAccountActivity.class);
                modifyAccountActivityIntent.putExtra("USER", user);
                startActivity(modifyAccountActivityIntent);
            } else if (v.getId() == buttonLogOut.getId()) {
                Intent loginActivityIntent = new Intent(this, LoginActivity.class);
                user = null;
                startActivity(loginActivityIntent);
            }
        } catch (Exception ex) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(ex.getMessage());
            alert.show();
        }
    }
}
