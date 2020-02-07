package com.example.lit_fits_application.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.lit_fits_application.entities.Company;
import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.GarmentType;
import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.Materials;
import com.example.lit_fits_application.entities.Mood;
import com.example.lit_fits_application.entities.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
    private ArrayList<Color> trendingColors;
    /**
     * List of trending Materials according to the Experts
     */
    private ArrayList<Material> trendingMaterials;
    /**
     * Address of the server
     */
    // private String uri;
    /**
     * The amount of garments
     */
    // Integer amountOfGarments;
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
        // uri = getResources().getString(R.string.uri);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Explode());
        Animation animationRightIn = AnimationUtils.loadAnimation(this, R.anim.right_in);
        buttonGoBack.startAnimation(animationRightIn);
        fillMaterialList();
        fillColorList();
        if (user.getGarments() != null) {
            fillLists();
            fillTableRow(tableRowHat, BodyPart.HAT);
            fillTableRow(tableRowTop, BodyPart.TOP);
            fillTableRow(tableRowBottom, BodyPart.BOTTOM);
            fillTableRow(tableRowShoes, BodyPart.SHOE);
            fillTableRow(tableRowOther, BodyPart.OTHER);
            fillTableRowToBuy(getGarmentToBuy());
        }
    }

    /**
     * Gets a random garment to recommend the user to buy
     */
    private Garment getGarmentToBuy() {
        /*
            // get random garment by random id
            GarmentClientInterface garmentClientInterface = getAmountOfGarments();
            Random random = new Random();
            // it's a shitty solution but there's no time for anything better
            Long aux = random.nextLong() + 1;
            while (aux > amountOfGarments) {
                aux = random.nextLong();
            }
            getGarmentFromServer(garmentClientInterface, aux);
        */
        List<Garment> auxCompanyGarments = new ArrayList<>();
        Company auxCompany = new Company("A1111111A", "abcd", "Some Company", "111223344", "some@com.pany", new Date(), new Date(), auxCompanyGarments);
        Garment auxGarment6 = createAuxGarment6(auxCompany);
        auxCompanyGarments.add(auxGarment6);
        auxCompany.setGarments(auxCompanyGarments);
        return auxGarment6;
    }

    private Garment createAuxGarment6(Company auxCompany) {
        Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.auxsix);
        Set<Color> auxColors1 = new HashSet<>();
        Color auxColor1 = new Color("Green");
        Color auxColor2 = new Color("Yellow");
        auxColors1.add(auxColor1);
        auxColors1.add(auxColor2);
        Set<Material> auxMaterials1 = new HashSet<>();
        Material auxMaterial1 = new Material("Leather");
        Material auxMaterial2 = new Material("Silk");
        auxMaterials1.add(auxMaterial1);
        auxMaterials1.add(auxMaterial2);
        return new Garment("12345", "cute.jpg", "Some Guy", 123.45, Mood.FORMAL, BodyPart.FULLBODY, GarmentType.BEANIE, true, true, false, "", auxCompany, auxColors1, auxMaterials1, picture);
    }

    /*
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
     */

    /*
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
     */

    private void fillTableRowToBuy(Garment garment) {
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(garment.getPicture());
        TextView textViewBarcode = new TextView(this);
        textViewBarcode.setText(garment.getBarcode());
        TextView textViewCompany = new TextView(this);
        textViewCompany.setText(garment.getCompany().getFullName());
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
                imageView.setImageBitmap(recommendationsHashMap.get(bodyPart).getPicture());
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
        /*
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
         */
        trendingMaterials = new ArrayList<>();
        Material auxMaterial1 = new Material("Leather");
        Material auxMaterial2 = new Material("Cotton");
        Material auxMaterial3 = new Material("Silk");
        trendingMaterials.add(auxMaterial1);
        trendingMaterials.add(auxMaterial2);
        trendingMaterials.add(auxMaterial3);
    }
    /*
        private void getMaterialResponse(Response<Materials> response) {
            if (response.code() == 200) {
                trendingMaterials = response.body();
                //trendingMaterials.setMaterials(response.body().getMaterials());
            } else if (response.code() == 500) {
                createAlertDialog("Unknown Error");
            }
        }
    */
    /**
     * Gets all the recommended Colors from the server and fills the List with them
     */
    private void fillColorList() {
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
        trendingColors = new ArrayList<>();
        Color auxColor1 = new Color("Black");
        Color auxColor2 = new Color("White");
        Color auxColor3 = new Color("Red");
        trendingColors.add(auxColor1);
        trendingColors.add(auxColor2);
        trendingColors.add(auxColor3);
    }
    /*
        private void getColorsResponse(Response<Colors> response) {
            if (response.code() == 200) {
                trendingColors = new Colors();
                trendingColors.setColors(response.body().getColors());
            } else if (response.code() == 500) {
                createAlertDialog("Unknown Error");
            }
        }
    */

    /**
     * Assigns the elements
     */
    private void findById() {
        buttonGoBack = findViewById(R.id.buttonGoBack);
        buttonGoBack.setBackgroundColor(android.graphics.Color.RED);
        tableRecommendations = findViewById(R.id.tableRecommendations);
        tableRowToBuy = findViewById(R.id.tableRowToBuy);
    }

    @Override
    public void onClick(View v) {
        MediaPlayer.create(this, R.raw.oof);
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
