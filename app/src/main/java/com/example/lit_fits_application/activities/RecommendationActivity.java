package com.example.lit_fits_application.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.ExpertClientInterface;
import com.example.lit_fits_application.clients.GarmentClientInterface;
import com.example.lit_fits_application.entities.BodyPart;
import com.example.lit_fits_application.entities.Color;
import com.example.lit_fits_application.entities.Colors;
import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.Materials;
import com.example.lit_fits_application.entities.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
     * Table row for hats
     */
    private TableRow tableRowHat;
    /**
     * Table row for tops
     */
    private TableRow tableRowTop;
    /**
     * Table row for bottoms
     */
    private TableRow tableRowBottom;
    /**
     * Table row for shoes
     */
    private TableRow tableRowShoes;
    /**
     * Table row for other
     */
    private TableRow tableRowOther;
    /**
     * User logged in
     */
    private User user;
    /**
     * List of trending Colors according to the Experts
     */
    private Colors trendingColors;
    /**
     * List of trending Materials according to the Experts
     */
    private Materials trendingMaterials;
    /**
     * Address of the server
     */
    private String uri;
    /**
     * The amount of garments
     */
    Integer amountOfGarments;
    /**
     * HashMap with the recommended Garments
     */
    private HashMap<BodyPart, Garment> recommendationsHashMap;
    private Bundle extrasBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        findById();
        buttonGoBack.setOnClickListener(this);
        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        user = (User) extrasBundle.get("USER");
        uri = getResources().getString(R.string.uri);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Explode());
        fillMaterialList();
        fillColorList();
        if (user.getGarments() != null) {
            fillLists();
            fillTableRow(tableRowHat, BodyPart.HAT);
            fillTableRow(tableRowTop, BodyPart.TOP);
            fillTableRow(tableRowBottom, BodyPart.BOTTOM);
            fillTableRow(tableRowShoes, BodyPart.SHOE);
            fillTableRow(tableRowOther, BodyPart.OTHER);
            getGarmentToBuy();
        }
    }

    /**
     * Gets a random garment to recommend the user to buy
     */
    private void getGarmentToBuy() {
        // get random garment by random id
        GarmentClientInterface garmentClientInterface = getAmountOfGarments();
        Random random = new Random();
        // it's a shitty solution but there's no time for anything better
        Long aux = random.nextLong() + 1;
        while (aux > amountOfGarments) {
            aux = random.nextLong();
        }
        getGarmentFromServer(garmentClientInterface, aux);
    }

    private void getGarmentFromServer(GarmentClientInterface garmentClientInterface, Long aux) {
        Call<Garment> garmentCall = garmentClientInterface.findGarment(aux.toString());
        garmentCall.enqueue(new Callback<Garment>() {
            @Override
            public void onResponse(Call<Garment> call, Response<Garment> response) {
                if (response.code() == 200) {
                    fillTableRowToBuy(response);
                } else {
                    createAlertDialog("Unknown Error");
                }
            }

            @Override
            public void onFailure(Call<Garment> call, Throwable t) {

            }
        });
    }

    @NotNull
    private GarmentClientInterface getAmountOfGarments() {
        GarmentClientInterface garmentClientInterface = new ClientFactory().getGarmentClient(uri);
        Call<Integer> amountOfGarmentsCall = garmentClientInterface.countREST();
        amountOfGarmentsCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == 200) {
                    amountOfGarments = response.body();
                } else {
                    createAlertDialog("Unknown Error");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                createAlertDialog("Unknown Error");
            }
        });
        return garmentClientInterface;
    }

    private void fillTableRowToBuy(Response<Garment> response) {
        ImageView imageView = new ImageView(this);
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(response.body().getPicture(), 0, response.body().getPicture().length);
        imageView.setImageBitmap(imageBitmap);
        TextView textViewBarcode = new TextView(this);
        textViewBarcode.setText(response.body().getBarcode());
        TextView textViewCompany = new TextView(this);
        textViewCompany.setText(response.body().getCompany().getFullName());
        tableRowToBuy.addView(imageView);
        tableRowToBuy.addView(textViewBarcode);
        tableRowToBuy.addView(textViewCompany);
    }

    /**
     * Fills a given table row with the data of a given garment type
     */
    private void fillTableRow(TableRow tableRowToFill, BodyPart bodyPart) {
        if (!recommendationsHashMap.isEmpty()) {
            if (recommendationsHashMap.get(bodyPart) != null) {
                ImageView imageView = new ImageView(this);
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(recommendationsHashMap.get(bodyPart).getPicture(), 0, recommendationsHashMap.get(bodyPart).getPicture().length);
                imageView.setImageBitmap(imageBitmap);
                TextView textViewBarcode = new TextView(this);
                textViewBarcode.setText(recommendationsHashMap.get(bodyPart).getBarcode());
                TextView textViewCompany = new TextView(this);
                textViewCompany.setText(recommendationsHashMap.get(bodyPart).getCompany().getFullName());
                tableRowToFill.addView(imageView);
                tableRowToFill.addView(textViewBarcode);
                tableRowToFill.addView(textViewCompany);
                tableRecommendations.addView(tableRowToFill);
            }
        } else {
            createAlertDialog("Nothing to recommend, as you have no garments");
        }
    }

    /**
     * Discriminates the Garments based on what's trending and fills the lists wih them
     */
    private void fillLists() {
        List<Garment> auxGarmentList = user.getGarments().getGarments().stream().filter(garment -> garment.getColors().getColors().stream().equals(trendingColors)).collect(Collectors.toList());
        auxGarmentList = auxGarmentList.stream().filter(garment -> garment.getMaterials().getMaterials().stream().equals(trendingMaterials)).collect(Collectors.toList());
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
        Call<Materials> call = expertClientInterface.recommendedMaterials();

        call.enqueue(new Callback<Materials>() {
            @Override
            public void onResponse(Call<Materials> call, Response<Materials> response) {
                createAlertDialog(response.body().toString());
                getMaterialResponse(response);
            }

            @Override
            public void onFailure(Call<Materials> call, Throwable t) {
                createAlertDialog(t.getMessage());
                createAlertDialog("Something failed");
            }
        });


    }

    private void getMaterialResponse(Response<Materials> response) {
        if (response.code() == 200) {
            trendingMaterials = response.body();
            //trendingMaterials.setMaterials(response.body().getMaterials());
        } else if (response.code() == 500) {
            createAlertDialog("Unknown Error");
        }
    }

    /**
     * Gets all the recommended Colors from the server and fills the List with them
     */
    private void fillColorList() {
        ExpertClientInterface expertClientInterface = new ClientFactory().getExpertClient(uri);
        Call<Colors> call = expertClientInterface.recommendedColors();
        try {
            trendingColors.setColors(call.execute().body().getColors());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        call.enqueue(new Callback<Colors>() {
            @Override
            public void onResponse(Call<Colors> call, Response<Colors> response) {
                createAlertDialog(response.body().getColors().toString());
                getColorsResponse(response);
            }

            @Override
            public void onFailure(Call<Colors> call, Throwable t) {
                createAlertDialog(t.getMessage());
            }
        });

         */
    }

    private void getColorsResponse(Response<Colors> response) {
        if (response.code() == 200) {
            trendingColors = new Colors();
            trendingColors.setColors(response.body().getColors());
        } else if (response.code() == 500) {
            createAlertDialog("Unknown Error");
        }
    }

    /**
     * Assigns the elements
     */
    private void findById() {
        buttonGoBack =findViewById(R.id.buttonGoBack);
        tableRecommendations =findViewById(R.id.tableRecommendations);
        tableRowToBuy = findViewById(R.id.tableRowToBuy);
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
