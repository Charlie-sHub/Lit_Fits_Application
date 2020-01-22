package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
public class TastesActivity extends AppCompatActivity {

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
     *
     */
    private void findById() {
        btnBack.findViewById(R.id.btnBack);
        tableMaterials.findViewById(R.id.tableMaterials);
        tableColors.findViewById(R.id.tableColors);
    }

    /**
     *
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
     *
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
     * Creates a new row for every material that receives.
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
     *
     *
     * @param tableLayout
     */
    private void fillEmptyTable(TableLayout tableLayout) {
        TableRow emptyTableRow = new TableRow(this);

        TextView emptyTextView = new TextView(this);
        emptyTextView.setText("This information is empty.");

        tableLayout.addView(emptyTableRow);
    }
}
