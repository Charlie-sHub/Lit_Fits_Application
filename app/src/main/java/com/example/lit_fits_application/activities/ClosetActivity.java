package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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

    private TableLayout garmentTable;

    private TableRow garmentRow;

    private List<Garment> garmentList;

    public TableLayout getGarmentTable() {
        return this.garmentTable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        
        fillGarmentTable();
    }
    
    private void fillGarmentTable() {
        
    }

    protected TableRow createNewTableRow(Garment garment) {
        TableRow garmentRow = new TableRow(this);

        ImageView imageView = new ImageView(this);
        TextView companyView = new TextView(this);

        //imageView.setImage(garment.getImagePath());
        companyView.setText(garment.getCompany().getFullName());

        garmentRow.addView(imageView);
        garmentRow.addView(companyView);

        Button deleteButton = new Button(this);
        // deleteButton.setOnClickListener(this.onDeleteButtonPress(this, garment));

        return garmentRow;
    }
}
