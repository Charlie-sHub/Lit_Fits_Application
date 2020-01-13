package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.ExpertClientInterface;
import com.example.lit_fits_application.clients.UserClientInterface;
import com.example.lit_fits_application.entities.Color;
import com.example.lit_fits_application.entities.FashionExpert;
import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.User;

import java.util.List;
import java.util.Set;

public class RecommendationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonGoBack;
    private TableLayout tableRecommendations;
    private Bundle extrasBundle;
    private User user;
    private Set<Color> trendingColors;
    private Set<Material> trendingMaterials;
    private String uri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        findById();
        buttonGoBack.setOnClickListener(this);
        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        user = (User) extrasBundle.get("USER");
        uri = extrasBundle.getString("URI");
        // Fill table with the recommendations

        // Fill trending colors
        // Fill trending materials
        ExpertClientInterface expertClientInterface = new ClientFactory().getUserClient(uri);
        Call<List<Color>> call = (Call<List<Color>>) expertClientInterface.recommendedColors();
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

    }

    private void findById() {
        buttonGoBack.findViewById(R.id.buttonGoBack);
        tableRecommendations.findViewById(R.id.tableRecommendations);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonGoBack.getId()) {
            Intent mainMenuActivityIntent = new Intent(this, MainMenuActivity.class);
            mainMenuActivityIntent.putExtra("USER", user);
            startActivity(mainMenuActivityIntent);
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
}
