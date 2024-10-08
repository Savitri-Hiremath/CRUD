package com.example.crud;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    TextView textView; // Correctly declare textView
    EditText editID, editFirstName, editLastName, editPhoneNumber, editEmail, editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        textView = findViewById(R.id.textViewMessage); // Assign the correct TextView
        editID = findViewById(R.id.editID);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);

        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonRetrieve = findViewById(R.id.buttonRetrieve);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        buttonRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveData();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    private void saveData() {
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String phoneNumber = editPhoneNumber.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String address = editAddress.getText().toString().trim();

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phoneNumber) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = db.insertData(firstName, lastName, phoneNumber, email, address);
        if (isInserted) {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveData() {
        Cursor cursor = db.retrieveData();

        // Check if the cursor is empty
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show(); // Display a toast message
            return; // Exit the method if no data is found
        }

        StringBuilder data = new StringBuilder();
        while (cursor.moveToNext()) {
            data.append("ID: ").append(cursor.getString(0)).append("\n");
            data.append("First Name: ").append(cursor.getString(1)).append("\n");
            data.append("Last Name: ").append(cursor.getString(2)).append("\n");
            data.append("Phone Number: ").append(cursor.getString(3)).append("\n");
            data.append("Email: ").append(cursor.getString(4)).append("\n");
            data.append("Address: ").append(cursor.getString(5)).append("\n\n");
        }
        textView.setText(data.toString()); // Set the text in the TextView
    }


    private void updateData() {
        String id = editID.getText().toString().trim();
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String phoneNumber = editPhoneNumber.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String address = editAddress.getText().toString().trim();

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(email) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdated = db.updateData(id, firstName, lastName, phoneNumber, email, address);
        if (isUpdated) {
            Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Error updating data", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        String id = editID.getText().toString().trim();
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(this, "ID is required for deletion", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer deletedRows = db.deleteData(id);
        if (deletedRows > 0) {
            Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Error deleting data or ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editID.setText("");
        editFirstName.setText("");
        editLastName.setText("");
        editPhoneNumber.setText("");
        editEmail.setText("");
        editAddress.setText("");
    }
}
