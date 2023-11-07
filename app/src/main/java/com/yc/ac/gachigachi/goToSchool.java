package com.yc.ac.gachigachi;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class goToSchool extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase 인스턴스 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 차량 정보 저장
        saveCarInfo("차량ID", "차량이름", "차량모델", "차량색상");

        // 차량 정보 불러오기
        getCarInfo("차량ID");
    }

    private void saveCarInfo(String carId, String carName, String carModel, String carColor) {
        Car car = new Car(carName, carModel, carColor);
        mDatabase.child("cars").child(carId).setValue(car);
    }

    private void getCarInfo(String carId) {
        mDatabase.child("cars").child(carId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Car car = dataSnapshot.getValue(Car.class);
                if (car != null) {
                    Log.d("CarInfo", "차량 이름: " + car.getName());
                    Log.d("CarInfo", "차량 모델: " + car.getModel());
                    Log.d("CarInfo", "차량 색상: " + car.getColor());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("CarInfo", "데이터 불러오기 실패", databaseError.toException());
            }
        });
    }

    public class Car {
        private String name;
        private String model;
        private String color;

        public Car() {
            // Default constructor required for calls to DataSnapshot.getValue(Car.class)
        }

        public Car(String name, String model, String color) {
            this.name = name;
            this.model = model;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public String getModel() {
            return model;
        }

        public String getColor() {
            return color;
        }
    }
}
