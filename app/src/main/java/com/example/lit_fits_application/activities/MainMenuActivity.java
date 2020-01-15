package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.entities.User;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRecommendation;
    private Button buttonTastes;
    private Button buttonCloset;
    private Button buttonModifyAccount;
    private Button buttonLogOut;
    private User user;
    private Bundle extrasBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findButtons();
        setListenerForButtons();
        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        user = (User) extrasBundle.get("USER");
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
