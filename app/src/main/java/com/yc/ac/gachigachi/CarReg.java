package com.yc.ac.gachigachi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarReg extends AppCompatActivity {

    // 일반 정보 부분
    TextInputEditText editTextName;
    TextInputEditText editTextCarNumber;
    TextInputEditText editTextAddress;
    TextInputEditText editTextPhoneNumber;

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
    private static final String TAG= "firestore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_reg);

        // 일반 정보 부분
        editTextName = findViewById(R.id.editTextName);
        editTextCarNumber = findViewById(R.id.editTextCarNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        
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

        // cancelButton 클릭시 동작
        cancelButton.setOnClickListener(v -> {
            MaterialAlertDialogBuilder(context)
                    .setTitle(resources.getString(R.string.title))
                    .setMessage(resources.getString(R.string.supporting_text))
                    .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                        // Respond to negative button press

                    };
                    .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                        // Respond to positive button press
                        finish();
                    }
                    .show()
        });



        // submitButton 클릭시 동작
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 시간표 후처리
                Map<String, Object> mon = new HashMap<>();
                mon.put("goSchool", editTextMondayArrival);
                mon.put("goHome", editTextMondayDeparture);
                mon.put("isShow_day", true);
                Map<String, Object> tue = new HashMap<>();
                tue.put("goSchool", editTextTuesdayArrival);
                tue.put("goHome", editTextTuesdayDeparture);
                tue.put("isShow_day", true);
                Map<String, Object> wed = new HashMap<>();
                wed.put("goSchool", editTextWednesdayArrival);
                wed.put("goHome", editTextWednesdayDeparture);
                wed.put("isShow_day", true);
                Map<String, Object> thu = new HashMap<>();
                thu.put("goSchool", editTextThursdayArrival);
                thu.put("goHome", editTextThursdayDeparture);
                thu.put("isShow_day", true);
                Map<String, Object> fri = new HashMap<>();
                fri.put("goSchool", editTextFridayArrival);
                fri.put("goHome", editTextFridayDeparture);
                fri.put("isShow_day", true);
                List<Map<String, Object>> timetable = new ArrayList<>();
                timetable.add(mon);
                timetable.add(tue);
                timetable.add(wed);
                timetable.add(thu);
                timetable.add(fri);

                // 입력한 데이터를 받아와서 CarList 객체에 삽입
                CarList carList = new CarList(
                        editTextName.toString(),
                        editTextCarNumber.toString(),
                        editTextAddress.toString(),
                        editTextPhoneNumber.toString(),
                        true,
                        timetable
                );

                // 자동 생성된 ID로 문서 생성
                db.collection("car")
                        .add(carList)
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
        });



    }
}

