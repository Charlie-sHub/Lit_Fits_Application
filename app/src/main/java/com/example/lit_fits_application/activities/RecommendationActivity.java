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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.ExpertClientInterface;
import com.example.lit_fits_application.entities.BodyPart;
import com.example.lit_fits_application.entities.Color;
import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Activity where Garments are recommended to the User
 *
 * @author Carlos Mendez
 */
public class RecommendationActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Button to go back to the main menu
     */
    private Button buttonGoBack;
    /**
     * The table with the recommendations
     */
    private TableLayout tableRecommendations;
    /**
     * Table Row with a recommendation to buy
     */
    private TableRow tableRowToBuy;
    /**
     * Image of the Garment to buy
     */
    private ImageView imageViewToBuy;
    /**
     * Barcode of the Garment to buy
     */
    private TextView textViewBarcode;
    /**
     * Price of the Garment to buy
     */
    private TextView textViewPrice;
    /**
     * User logged in
     */
    private User user;
    /**
     * List of trending Colors according to the Experts
     */
    private List<Color> trendingColors;
    /**
     * List of trending Materials according to the Experts
     */
    private List<Material> trendingMaterials;
    /**
     * Address of the server
     */
    private String uri;
    /**
     * HashMap with the recommended Garments
     */
    private HashMap<BodyPart, Garment> recommendationsHashMap;
    private Bundle extrasBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        findById();
        buttonGoBack.setOnClickListener(this);
        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        user = (User) extrasBundle.get("USER");
        uri = getResources().getString(R.string.uri);
        fillColorList();
        fillMaterialList();
        fillLists();
        // Fill table with the recommendations
        // Fill the row to buy
    }

    /**
     * Discriminates the Garments based on what's trending and fills the lists wih them
     */
    private void fillLists() {
        List<Garment> auxGarmentList = user.getGarments().stream().filter(garment -> garment.getColors().stream().equals(trendingColors)).collect(Collectors.toList());
        auxGarmentList = auxGarmentList.stream().filter(garment -> garment.getMaterials().stream().equals(trendingMaterials)).collect(Collectors.toList());
        List<Garment> tops = auxGarmentList.stream().filter(garment -> garment.getBodyPart().equals(BodyPart.TOP)).collect(Collectors.toList());
        List<Garment> bottoms = auxGarmentList.stream().filter(garment -> garment.getBodyPart().equals(BodyPart.BOTTOM)).collect(Collectors.toList());
        List<Garment> shoes = auxGarmentList.stream().filter(garment -> garment.getBodyPart().equals(BodyPart.SHOE)).collect(Collectors.toList());
        List<Garment> hats = auxGarmentList.stream().filter(garment -> garment.getBodyPart().equals(BodyPart.HAT)).collect(Collectors.toList());
        List<Garment> other = auxGarmentList.stream().filter(garment -> garment.getBodyPart().equals(BodyPart.OTHER)).collect(Collectors.toList());
        shuffleLists(tops, bottoms, shoes, hats, other);
        fillHashMap(tops, bottoms, shoes, hats, other);
    }

    /**
     * Shuffles the lists to randomize the Garments recommended
     *
     * @param tops
     * @param bottoms
     * @param shoes
     * @param hats
     * @param other
     */
    private void shuffleLists(List<Garment> tops, List<Garment> bottoms, List<Garment> shoes, List<Garment> hats, List<Garment> other) {
        Collections.shuffle(tops);
        Collections.shuffle(bottoms);
        Collections.shuffle(shoes);
        Collections.shuffle(hats);
        Collections.shuffle(other);
    }

    /**
     * Fills the HashMap
     *
     * @param tops
     * @param bottoms
     * @param shoes
     * @param hats
     * @param other
     */
    private void fillHashMap(List<Garment> tops, List<Garment> bottoms, List<Garment> shoes, List<Garment> hats, List<Garment> other) {
        recommendationsHashMap.put(BodyPart.TOP, tops.get(1));
        recommendationsHashMap.put(BodyPart.BOTTOM, bottoms.get(1));
        recommendationsHashMap.put(BodyPart.SHOE, shoes.get(1));
        recommendationsHashMap.put(BodyPart.HAT, hats.get(1));
        recommendationsHashMap.put(BodyPart.OTHER, other.get(1));
    }

    /**
     * Gets all the recommended Materials from the server and fills the List with them
     */
    private void fillMaterialList() {
        ExpertClientInterface expertClientInterface = new ClientFactory().getExpertClient(uri);
        Call<List<Material>> call = (Call<List<Material>>) expertClientInterface.recommendedMaterials();
        call.enqueue(new Callback<List<Material>>() {
            @Override
            public void onResponse(Call<List<Material>> call, Response<List<Material>> response) {
                if (response.code() == 200) {
                    trendingMaterials = response.body();
                } else if (response.code() == 500) {
                    createAlertDialog("Unknown Error");
                }
            }

            @Override
            public void onFailure(Call<List<Material>> call, Throwable t) {
                createAlertDialog(t.getMessage());
            }
        });
    }

    /**
     * Gets all the recommended Colors from the server and fills the List with them
     */
    private void fillColorList() {
        ExpertClientInterface expertClientInterface = new ClientFactory().getExpertClient(uri);
        Call<List<Color>> call = (Call<List<Color>>) expertClientInterface.recommendedColors();
        call.enqueue(new Callback<List<Color>>() {
            @Override
            public void onResponse(Call<List<Color>> call, Response<List<Color>> response) {
                if (response.code() == 200) {
                    trendingColors = response.body();
                } else if (response.code() == 500) {
                    createAlertDialog("Unknown Error");
                }
            }

            @Override
            public void onFailure(Call<List<Color>> call, Throwable t) {
                createAlertDialog(t.getMessage());
            }
        });
    }

    /**
     * Assigns the elements
     */
    private void findById() {
        buttonGoBack.findViewById(R.id.buttonGoBack);
        tableRecommendations.findViewById(R.id.tableRecommendations);
        tableRowToBuy.findViewById(R.id.tableRowToBuy);
        imageViewToBuy.findViewById(R.id.imageViewToBuy);
        textViewBarcode.findViewById(R.id.textViewBarcode);
        textViewPrice.findViewById(R.id.textViewPrice);
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
