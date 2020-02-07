package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

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
     * Button to log out of Lit Fits
     */
    private Button buttonLogOut;
    private ImageView litFitsLogo;
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
        findViews();
        animate();
        setListenerForButtons();
        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        user = (User) extrasBundle.get("USER");
        // user.getGarments().getGarments().stream().forEach(garment -> Log.println(Log.INFO , "garment Id: ", String.valueOf(garment.getId())));
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Explode());
    }

    private void animate() {
        Animation animationZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_back_in);
        Animation animationLeftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
        Animation animationRightIn = AnimationUtils.loadAnimation(this, R.anim.right_in);
        buttonCloset.startAnimation(animationRightIn);
        buttonLogOut.startAnimation(animationLeftIn);
        buttonModifyAccount.startAnimation(animationRightIn);
        buttonRecommendation.startAnimation(animationLeftIn);
        buttonTastes.startAnimation(animationRightIn);
        litFitsLogo.startAnimation(animationZoomIn);
    }

    private void setListenerForButtons() {
        buttonCloset.setOnClickListener(this);
        buttonRecommendation.setOnClickListener(this);
        buttonTastes.setOnClickListener(this);
        buttonModifyAccount.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);
    }

    private void findViews() {
        buttonCloset = findViewById(R.id.buttonCloset);
        buttonTastes = findViewById(R.id.buttonTastes);
        buttonRecommendation = findViewById(R.id.buttonRecommendation);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonModifyAccount = findViewById(R.id.buttonModifyAccount);
        litFitsLogo = findViewById(R.id.imageViewLogo);
    }

    @Override
    public void onClick(View v) {
        MediaPlayer.create(this, R.raw.oof);
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
            ex.printStackTrace();
        }
    }
}
