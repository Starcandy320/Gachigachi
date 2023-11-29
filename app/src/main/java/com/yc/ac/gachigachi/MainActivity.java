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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.checkerframework.checker.units.qual.A;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> arrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firestore에서 데이터 읽어오기
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // 기존 데이터를 ArrayList에서 지우기
                            arrayList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // User 클래스가 있다고 가정하고, "User.class"를 여러분의 실제 클래스로 교체해주세요
                                User user = document.toObject(User.class);

                                // ArrayList에 사용자 추가
                                arrayList.add(user);
                            }

                            // 어댑터에게 데이터가 변경되었음을 알림
                            adapter.notifyDataSetChanged();
                        } else {
                            // 여기서 에러를 처리하세요
                            Toast.makeText(MainActivity.this, "데이터 로딩 실패", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "데이터 로딩 실패", task.getException());
                        }
                    }
                });

        adapter = new CustomAdapter(arrayList,this);
        recyclerView.setAdapter(adapter); //리사이클러 뷰에 어뎁터 연결
    }
}
