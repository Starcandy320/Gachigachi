package com.yc.ac.gachigachi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CarReg extends AppCompatActivity {
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_reg);
    Button submit = findViewById(R.id.submitButton);

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(CarReg.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    });
    }
}