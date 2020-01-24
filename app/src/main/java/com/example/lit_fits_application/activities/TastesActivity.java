package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.UserClientInterface;
import com.example.lit_fits_application.entities.Color;
import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The activity to check the tastes of the user.
 *
 * @author Asier Vila
 */
public class TastesActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The button that the user will press to go back.
     */
    private Button btnBack;

    private Bundle extrasBundle;

    private String uri;

    /**
     * The table that will contain the colors that the user likes.
     */
    private TableLayout tableColors;

    /**
     * The table that will contain the material that the user likes.
     */
    private TableLayout tableMaterials;

    /**
     * The user that is currently logged.
     */
    public User user;

    private boolean answer;

    private final String title = "Confirm delete operation";
    private final String message = "Are you sure you want to delete this from your tastes?";

    public boolean getAnswer() {
        return this.answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public TableLayout getTableColors() {
     return this.tableColors;
    }

    public TableLayout getTableMaterials() {
        return this.tableMaterials;
    }

    /**
     * Everything that has to happen when creating the activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tastes);

        extrasBundle = new Bundle();
        extrasBundle = getIntent().getExtras();
        user = (User) extrasBundle.get("USER");
        uri = getResources().getString(R.string.uri);

        this.findById();

        fillColorTable();
        fillMaterialTable();
    }

    /**
     * Finds all the objects of the activity using their id.
     */
    private void findById() {
        btnBack.findViewById(R.id.btnBack);
        tableMaterials.findViewById(R.id.tableMaterials);
        tableColors.findViewById(R.id.tableColors);
    }

    /**
     * Fills the color table with all the colors that the user likes.
     */
    private void fillColorTable() {
        if (!user.getLikedColors().isEmpty()) {
            for (Color color : user.getLikedColors()) {
                this.createNewColorTableRow(color, this);
            }
        } else {
            fillEmptyTable(tableColors);
        }
    }

    private void createNewColorTableRow(Color color, TastesActivity tastesActivity) {

        TableRow colorRow = new TableRow(tastesActivity);
        int id = tastesActivity.getTableColors().getChildCount();

        colorRow.setId(id);

        TextView colorNameView = new TextView(tastesActivity);
        colorNameView.setText(color.getName());

        Button deleteButton = this.createDeleteButton(tastesActivity, id, color);

        tastesActivity.getTableColors().addView(colorRow);
    }

    private Button createDeleteButton(TastesActivity tastesActivity, int rowId, Color color) {
        Button deleteButton = new Button(tastesActivity);

        deleteButton.setId(rowId);
        deleteButton.setOnClickListener(this.deleteSelectedRecord(tastesActivity, rowId, color));

        return deleteButton;
    }

    private View.OnClickListener deleteSelectedRecord(TastesActivity tastesActivity, int rowId, Color color) {

        return (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLikedColor(tastesActivity, rowId, color);
            }
        });

    }

    private void deleteLikedColor(TastesActivity tastesActivity, int rowId, Color color) {

        User changeUser = this.user;

        changeUser.removeLikedColor(color);

        UserClientInterface userClient = new ClientFactory().getUserClient(uri);
        Call<Void> voidCall = userClient.editUser(user);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    createAlertDialog("This color has been deleted.");
                    tastesActivity.getTableColors().removeView(tastesActivity.getTableColors().getChildAt(rowId));

                } else if (response.code() == 404) {
                    createAlertDialog("The server did not found this user's profile.");

                } else if (response.code() == 500) {
                    createAlertDialog("Server unknown error.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        } );
    }

    private void fillMaterialTable() {
        if (!user.getLikedMaterials().isEmpty()) {
            for (Material material: user.getLikedMaterials()) {
                this.createNewColorTableRow(material, this);
            }
        } else {
            fillEmptyTable(tableMaterials);
        }
    }

    private void createNewColorTableRow(Material material, TastesActivity tastesActivity) {

        TableRow materialRow = new TableRow(tastesActivity);
        int id = tastesActivity.getTableMaterials().getChildCount();

        materialRow.setId(id);

        TextView materialNameView = new TextView(tastesActivity);
        materialNameView.setText(material.getName());

        Button deleteButton = this.createDeleteButton(tastesActivity, id, material);

        tastesActivity.getTableMaterials().addView(materialRow);
    }

    private Button createDeleteButton(TastesActivity tastesActivity, int rowId, Material material) {
        Button deleteButton = new Button(tastesActivity);

        deleteButton.setId(rowId);
        deleteButton.setOnClickListener(this.deleteSelectedRecord(tastesActivity, rowId, material));

        return deleteButton;
    }

    private View.OnClickListener deleteSelectedRecord(TastesActivity tastesActivity, int rowId, Material material) {

        return (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLikedMaterial(tastesActivity, rowId, material);
            }
        });
    }

    private void deleteLikedMaterial(TastesActivity tastesActivity, int rowId, Material material) {

        User changeUser = this.user;

        changeUser.removeLikedMaterial(material);

        UserClientInterface userClient = new ClientFactory().getUserClient(uri);
        Call<Void> voidCall = userClient.editUser(user);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    createAlertDialog("This material has been deleted.");
                    tastesActivity.getTableMaterials().removeView(tastesActivity.getTableMaterials().getChildAt(rowId));

                } else if (response.code() == 404) {
                    createAlertDialog("The server did not found this user's profile.");

                } else if (response.code() == 500) {
                    createAlertDialog("Server unknown error.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        } );
    }


    private void createAlertDialog(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.show();
    }

    /**
     * This method is called when a table of user tastes has not records to fill it.
     *
     * @param tableLayout The table that will be filled with the "empty information" message.
     */
    private void fillEmptyTable(TableLayout tableLayout) {
        TableRow emptyTableRow = new TableRow(this);

        TextView emptyTextView = new TextView(this);
        emptyTextView.setText("This information is empty.");

        tableLayout.addView(emptyTableRow);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnBack.getId()) {
            Intent mainMenuActivityIntent = new Intent(this, MainMenuActivity.class);
            mainMenuActivityIntent.putExtra("USER", user);
            startActivity(mainMenuActivityIntent);
        }
    }

    /**
     * This method opens a confirmation dialog and returns the answer as a boolean.
     *
     * @param message
     * @return "True" if the answer is a confirmation, "False" if not.
     */
    private AlertDialog createConfirmationDialog(String message, String title, TastesActivity tastesActivity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                tastesActivity.setAnswer(true);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                tastesActivity.setAnswer(false);
            }
        });

        builder.setMessage(message);
        builder.setTitle(title);

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }
}
