package com.example.lit_fits_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lit_fits_application.R;
import com.example.lit_fits_application.clients.ClientFactory;
import com.example.lit_fits_application.clients.UserClientInterface;
import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The activity to check the tastes of the user.
 *
 * @author Asier Vila
 */
public class ClosetActivity extends AppCompatActivity {

    private String uri;

    private User user;

    private Button btnBack;

    private TableLayout garmentTable;

    private TableRow garmentRow;

    private List<Garment> garmentList;

    public void setUser(User user) {
        this.user = user;
    }

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

        if (!user.getGarments().isEmpty()) {
            for (Garment garment: user.getGarments()) {
                this.createNewGarmentTableRow(garment, this);
            }
        } else {
            fillEmptyTable(garmentTable);
        }
    }

    private void createNewGarmentTableRow(Garment garment, ClosetActivity closetActivity) {

        TableRow garmentRow = new TableRow(closetActivity);
        int id = closetActivity.getGarmentTable().getChildCount();

        garmentRow.setId(id);

        TextView garmentNameView = new TextView(closetActivity);
        garmentNameView.setText(garment.getBarcode());

        Button deleteButton = this.createDeleteButton(closetActivity, id, garment);

        closetActivity.getGarmentTable().addView(garmentRow);
    }

    private Button createDeleteButton(ClosetActivity closetActivity, int rowId, Garment garment) {
        Button deleteButton = new Button(closetActivity);

        deleteButton.setId(rowId);
        deleteButton.setOnClickListener(this.deleteSelectedRecord(closetActivity, rowId, garment));

        return deleteButton;
    }

    private View.OnClickListener deleteSelectedRecord(ClosetActivity closetActivity, int rowId, Garment garment) {

        return (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSavedGarment(closetActivity, rowId, garment);
            }
        });
    }

    private void deleteSavedGarment(ClosetActivity closetActivity, int rowId, Garment garment) {

        User changeUser = this.user;

        changeUser.getGarments().remove(garment);

        UserClientInterface userClient = new ClientFactory().getUserClient(uri);
        Call<Void> voidCall = userClient.editUser(user);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    createAlertDialog("This garment has been deleted.");
                    closetActivity.getGarmentTable().removeView(closetActivity.getGarmentTable().getChildAt(rowId));

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

    private void fillEmptyTable(TableLayout tableLayout) {
        TableRow emptyTableRow = new TableRow(this);

        TextView emptyTextView = new TextView(this);
        emptyTextView.setText("This information is empty.");

        tableLayout.addView(emptyTableRow);
    }
}
