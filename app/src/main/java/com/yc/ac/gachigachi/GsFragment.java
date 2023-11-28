package com.yc.ac.gachigachi;

import static android.content.ContentValues.TAG;

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

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<board_Item> goSchool;

    public GsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gs, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_gs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        goSchool = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // 기존 데이터를 ArrayList에서 지우기
                            goSchool.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                board_Item boardItem = document.toObject(board_Item.class);
                                // ArrayList에 사용자 추가
                                goSchool.add(boardItem);
                            }

                            // 어댑터에게 데이터가 변경되었음을 알림
                            adapter.notifyDataSetChanged();
                        } else {
                            // 여기서 에러를 처리하세요
                            Toast.makeText(getContext(), "데이터 로딩 실패", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "데이터 로딩 실패", task.getException());
                        }
                    }
                });

//        gs_ListAdapter adapter1 = new gs_ListAdapter(goSchool);
        adapter = new gs_ListAdapter(goSchool);
        recyclerView.setAdapter(adapter);


//스와이프 콜 일단 주석처리
//        SwipeCall swipeCall = new SwipeCall(requireContext(), adapter1);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCall);
//        itemTouchHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }
}
