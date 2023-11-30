package com.yc.ac.gachigachi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CarReg extends AppCompatActivity {

    // 일반 정보 부분
    TextInputEditText editTextName;
    TextInputEditText editTextCarNumber;
    TextInputEditText editTextAddress;
    TextInputEditText editTextPhoneNumber;
    TextInputEditText editTextStudentID;

    // 시간표 부분
    TextInputEditText editTextMondayArrival;
    TextInputEditText editTextTuesdayArrival;
    TextInputEditText editTextWednesdayArrival;
    TextInputEditText editTextThursdayArrival;
    TextInputEditText editTextFridayArrival;
    TextInputEditText editTextMondayDeparture;
    TextInputEditText editTextTuesdayDeparture;
    TextInputEditText editTextWednesdayDeparture;
    TextInputEditText editTextThursdayDeparture;
    TextInputEditText editTextFridayDeparture;

    // 버튼 부분
    MaterialButton submitButton;
    MaterialButton cancelButton;

    // TAG String 상수 선언
    private static final String TAG = "firestore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_reg);

        // 일반 정보 부분
        editTextName = findViewById(R.id.editTextName);
        editTextCarNumber = findViewById(R.id.editTextCarNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextStudentID = findViewById(R.id.editTextStudentID);

        // 시간표 부분
        editTextMondayArrival = findViewById(R.id.editTextMondayArrival);
        editTextTuesdayArrival = findViewById(R.id.editTextTuesdayArrival);
        editTextWednesdayArrival = findViewById(R.id.editTextWednesdayArrival);
        editTextThursdayArrival = findViewById(R.id.editTextThursdayArrival);
        editTextFridayArrival = findViewById(R.id.editTextFridayArrival);
        editTextMondayDeparture = findViewById(R.id.editTextMondayDeparture);
        editTextTuesdayDeparture = findViewById(R.id.editTextTuesdayDeparture);
        editTextWednesdayDeparture = findViewById(R.id.editTextWednesdayDeparture);
        editTextThursdayDeparture = findViewById(R.id.editTextThursdayDeparture);
        editTextFridayDeparture = findViewById(R.id.editTextFridayDeparture);

        // button 부분
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);


        // Firestore 초기화
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // back키 클릭시 동작


        // cancelButton 클릭시 동작
        cancelButton.setOnClickListener(v -> new MaterialAlertDialogBuilder(CarReg.this)
                .setTitle("경고")
                .setMessage("입력된 정보를 저장하지 않고 뒤로 갑니다.")
                .setNegativeButton("취소", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("확인", (dialog, which) -> finish())
                .show());


        // submitButton 클릭시 동작
        // submitButton 클릭시 동작
        submitButton.setOnClickListener(v -> {
            // 입력값 확인
            if (validateInputs()) {
                // 시간표 후처리
                String mon = editTextMondayArrival.getText().toString() + "," + editTextMondayDeparture.getText().toString();
                String tue = editTextTuesdayArrival.getText().toString() + "," + editTextTuesdayDeparture.getText().toString();
                String wed = editTextWednesdayArrival.getText().toString() + "," + editTextWednesdayDeparture.getText().toString();
                String thu = editTextThursdayArrival.getText().toString() + "," + editTextThursdayDeparture.getText().toString();
                String fri = editTextFridayArrival.getText().toString() + "," + editTextFridayDeparture.getText().toString();

                String[] tt = {mon, tue, wed, thu, fri};
                ArrayList<String> timetable = new ArrayList<>(Arrays.asList(tt));

                // 입력한 데이터를 받아와서 CarList 객체에 삽입
                Map<String, Object> carList = new HashMap<>();
                carList.put("name", editTextName.getText().toString());
                carList.put("carNumber", editTextCarNumber.getText().toString());
                carList.put("address", editTextAddress.getText().toString());
                carList.put("phoneNumber", editTextPhoneNumber.getText().toString());
                carList.put("isShow", true);
                carList.put("studentID", editTextStudentID.getText().toString());
                carList.put("timetable", timetable);

                // 자동 생성된 ID로 문서 생성
                db.collection("carList")
                        .add(carList)
                        .addOnSuccessListener(documentReference -> {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                            Toast.makeText(CarReg.this, "성공적으로 저장되었습니다", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(TAG, "Error adding document", e);
                            Toast.makeText(CarReg.this, "저장에 실패하였습니다", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
    private boolean validateInputs() {
        if (editTextName.getText().toString().isEmpty() ||
                editTextCarNumber.getText().toString().isEmpty() ||
                editTextAddress.getText().toString().isEmpty() ||
                editTextPhoneNumber.getText().toString().isEmpty() ||
                editTextStudentID.getText().toString().isEmpty() ||
                editTextMondayArrival.getText().toString().isEmpty() ||
                editTextTuesdayArrival.getText().toString().isEmpty() ||
                editTextWednesdayArrival.getText().toString().isEmpty() ||
                editTextThursdayArrival.getText().toString().isEmpty() ||
                editTextFridayArrival.getText().toString().isEmpty() ||
                editTextMondayDeparture.getText().toString().isEmpty() ||
                editTextTuesdayDeparture.getText().toString().isEmpty() ||
                editTextWednesdayDeparture.getText().toString().isEmpty() ||
                editTextThursdayDeparture.getText().toString().isEmpty() ||
                editTextFridayDeparture.getText().toString().isEmpty()) {
            Toast.makeText(this, "입력값을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
