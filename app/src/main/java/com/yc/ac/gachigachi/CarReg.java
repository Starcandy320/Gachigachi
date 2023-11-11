package com.yc.ac.gachigachi;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CarReg extends AppCompatActivity {

    // editText 상단 정보
    TextInputEditText editTextName;
    TextInputEditText editTextCarNumber;
    TextInputEditText editTextAddress;
    TextInputEditText editTextPhoneNumber;

    // editText 하단 시간표
    TextInputEditText editText;

    // button
    MaterialButton submitButton;
    MaterialButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_reg);

        // editText 윗부분
        editTextName = findViewById(R.id.editTextName);
        editTextCarNumber = findViewById(R.id.editTextCarNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        // button 부분
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

        // firestore 초기화
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 입력한 데이터를 받아와서 가공
        // 시간표에 미입력시 공강 처리
        Map<String, Object> data = new HashMap<>();
        data.put("name", editTextName);
        data.put("carNumber", editTextCarNumber);
        data.put("Address", editTextAddress);
        data.put("phoneNumber", editTextPhoneNumber);
        data.put("isShow", true);

        // 자동 생성된 ID로 문서 생성
        db.collection("car")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(CarReg.this, "성공적으로 저장되었습니다", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(CarReg.this, "저장에 실패하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}