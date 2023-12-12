package com.yc.ac.gachigachi;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GhFragment extends Fragment {

    public GhFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gh, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_gh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("carList")
                .whereEqualTo("isShow", true)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<board_Item> goSchool = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String name = document.getString("name");
                            String address = document.getString("address");
                            String carNumber = document.getString("carNumber");
                            String phoneNumber = document.getString("phoneNumber");

                            Object timetableObject = document.get("timetable");
                            List<String> timetable = new ArrayList<>();
                            if (timetableObject instanceof List<?>) {
                                for (Object entry : (List<?>) timetableObject) {
                                    if (entry instanceof String) {
                                        timetable.add((String) entry);
                                    }
                                }
                            }
                            board_Item item = new board_Item(name, carNumber, phoneNumber, address, timetable);
                            goSchool.add(item);
                        }

                        listAdapter adapter2 = new listAdapter(goSchool, this::lastNumber);
                        recyclerView.setAdapter(adapter2);

                        SwipeCall swipeCall = new SwipeCall(requireContext(), adapter2);
                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCall);
                        itemTouchHelper.attachToRecyclerView(recyclerView);
                    } else {
                        Toast.makeText(getContext(), "데이터 로딩 실패", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "데이터 로딩 실패", task.getException());
                    }
                });

        return rootView;
    }

    private String lastNumber(String input) {
        String[] parts = input.split(",");
        if (parts.length > 0) {
            return parts[parts.length - 1];
        } else {
            return "";
        }
    }
}
