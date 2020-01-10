package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.entities.Color;
import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.User;

import java.util.Set;

public class RecommendationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonGoBack;
    private TableLayout tableRecommendations;
    private Bundle extrasBundle;
    private User user;
    private Set<Color> trendingColors;
    private Set<Material> trendingMaterials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        findById();
        buttonGoBack.setOnClickListener(this);
        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        user = (User) extrasBundle.get("USER");
        // Fill table with the recommendations
        // Fill trending colors
        // Fill trending materials
    }

    private void findById() {
        buttonGoBack.findViewById(R.id.buttonGoBack);
        tableRecommendations.findViewById(R.id.tableRecommendations);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonGoBack.getId()){
            Intent mainMenuActivityIntent = new Intent(this, MainMenuActivity.class);
            mainMenuActivityIntent.putExtra("USER", user);
            startActivity(mainMenuActivityIntent);
        }
    }
}
