package com.yc.ac.gachigachi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    Button save, read;
    EditText email, name, age, id;
    TextView data;
    int i = 1; //pk

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference(); //DatabaseReference의 인스턴스

        save = findViewById(R.id.submit);
        read = findViewById(R.id.read);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        age = findViewById(R.id.age);
        id = findViewById(R.id.id);
        data = findViewById(R.id.data);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUserName = name.getText().toString();
                String getUserEmail = email.getText().toString();
                String getUserAge = age.getText().toString();

                HashMap result = new HashMap<>();
                result.put("name", getUserName); //키, 값
                result.put("email", getUserEmail);
                result.put("age", getUserAge);

                writeUser(Integer.toString(i++), getUserName, getUserEmail, getUserAge);
            }
        });
    }

    private void writeUser(String userId, String name, String email, String age) {
        User user = new User(name, email, age);

        //데이터 저장
        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() { //데이터베이스에 넘어간 이후 처리
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"저장을 완료했습니다", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"저장에 실패했습니다" , Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void readUser(String userId) {
        //데이터 읽기
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                data.setText("이름: " + user.name + " 이메일: " + user.email + " 나이: " + user.age);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { //참조에 액세스 할 수 없을 때 호출
                Toast.makeText(getApplicationContext(),"데이터를 가져오는데 실패했습니다" , Toast.LENGTH_LONG).show();
            }
        });
    }
}
