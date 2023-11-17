package com.yc.ac.gachigachi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextCarNumber;
    private EditText editTextAddress;
    private EditText editPhoneNumber;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.editTextName);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editTextCarNumber = findViewById(R.id.editTextCarNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String phoneNumber = editPhoneNumber.getText().toString();
                String carNumber = editTextCarNumber.getText().toString();
                String address = editTextAddress.getText().toString();

                Map<String, Object> Car = new HashMap<>();
                Car.put("name", name);
                Car.put("phoneNumber", phoneNumber);
                Car.put("carNumber", carNumber);
                Car.put("address", address);

                db.collection("CarList").document(carNumber.toString())
                        .set(Car, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }

        });
    }
}