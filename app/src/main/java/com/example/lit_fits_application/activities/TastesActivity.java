package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.lit_fits_application.entities.Color;
import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.User;

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

    /**
     * Everything that has to happen when creating the activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tastes);

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
                tableColors.addView(this.createNewColorTableRow(color));
            }
        } else {
            fillEmptyTable(tableColors);
        }
    }

    /**
     * Creates a new row for every color that receives and returns it.
     *
     * @param color
     * @return
     */
    private TableRow createNewColorTableRow(Color color) {
        TableRow garmentRow = new TableRow(this);

        TextView colorNameView = new TextView(this);

        colorNameView.setText(color.getName());

        garmentRow.addView(colorNameView);

        return garmentRow;
    }

    /**
     * Fills the material table with all the colors that the user likes.
     */
    private void fillMaterialTable() {
        if (!user.getLikedMaterials().isEmpty()) {
            for (Material material : user.getLikedMaterials()) {
                tableMaterials.addView(this.createNewMaterialTableRow(material));
            }
        } else {
            fillEmptyTable(tableMaterials);
        }
    }

    /**
     * Creates a new row for every material that receives and returns it.
     *
     * @param material
     * @return
     */
    private TableRow createNewMaterialTableRow(Material material) {
        TableRow garmentRow = new TableRow(this);

        TextView materialNameView = new TextView(this);

        materialNameView.setText(material.getName());

        garmentRow.addView(materialNameView);

        return garmentRow;
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
    private boolean createConfirmationDialog(String message) {
        boolean ret = true;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.show();

        return ret;
    }
}
