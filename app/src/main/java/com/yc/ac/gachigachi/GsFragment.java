package com.yc.ac.gachigachi;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GsFragment extends Fragment {

    private gs_ListAdapter adapter1; // gs_ListAdapter 인스턴스를 클래스 변수로 선언
    private ArrayList<User> goSchool; // 데이터를 담을 ArrayList를 클래스 변수로 선언

    public GsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gs, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_gs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // ArrayList를 생성하여 Firestore에서 가져온 데이터를 저장
        goSchool = new ArrayList<>();

        // Firestore에서 데이터 읽어오기
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                goSchool.add(user);
                            }
                            // 어댑터에게 데이터가 변경되었음을 알림
                            adapter1.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "데이터 로딩 실패", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "데이터 로딩 실패", task.getException());
                        }
                    }
                });

        adapter1 = new gs_ListAdapter(getActivity(), goSchool); // adapter1을 초기화
        recyclerView.setAdapter(adapter1);

        SwipeCall swipeCall = new SwipeCall(requireContext(), adapter1);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCall);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }
}
