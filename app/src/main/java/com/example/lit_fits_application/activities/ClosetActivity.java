package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.entities.Garment;

import java.util.List;

/**
 * The activity to check the tastes of the user.
 *
 * @author Asier Vila
 */
public class ClosetActivity extends AppCompatActivity {

    private Button btnBack;

    private TableLayout closetTable;

    private TableRow garmentRow;

    public List<Garment> garmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
    }

    protected TableRow createNewTableRow(Garment garment) {
        TableRow garmentRow = new TableRow(this);

        TextView imageView = new TextView(this);
        TextView barcodeView = new TextView(this);
        TextView companyView = new TextView(this);

        imageView.setText(garment.getImagePath());
        barcodeView.setText(garment.getBarcode());
        companyView.setText(garment.getCompany().getFullName());

        garmentRow.addView(imageView);
        garmentRow.addView(barcodeView);
        garmentRow.addView(companyView);

        return garmentRow;
    }
}
