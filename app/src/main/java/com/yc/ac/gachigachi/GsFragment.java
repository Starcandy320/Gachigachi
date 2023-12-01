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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GsFragment extends Fragment {

    private static final String TAG = "firestore";

    public GsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gs, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_gs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Firestore에서 특정 필드만 가져오기
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("carList")
                .get()
                .addOnCompleteListener(task -> {
                    if (isAdded()) {  // Check if the fragment is attached to the activity
                        if (task.isSuccessful()) {
                            List<board_Item> goSchool = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String name = document.getString("name");
                                String address = document.getString("address");
                                String carNumber = document.getString("carNumber");
                                String phoneNumber = document.getString("phoneNumber");
                                ArrayList<String> timetable = (ArrayList<String>) document.get("timetable");

                                Calendar calendar = Calendar.getInstance();
                                Date date = calendar.getTime();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                                String currentDay = simpleDateFormat.format(date);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                int currentIndex = -1;

                                int dayIndex;
                                switch (currentDay) {
                                    case "Mon":
                                        dayIndex = 0;
                                        break;
                                    case "Tue":
                                        dayIndex = 1;
                                        break;
                                    case "Wed":
                                        dayIndex = 2;
                                        break;
                                    case "Thu":
                                        dayIndex = 3;
                                        break;
                                    case "Fri":
                                        dayIndex = 4;
                                        break;
                                    default:
                                        dayIndex = -1;
                                        break;
                                }

                                for (int i = 0; i < 5; i++) {
                                    if (i==dayIndex) {
                                        currentIndex = i;
                                        break;
                                    }
                                }

                                if (currentIndex != -1) {
                                    List<Integer> timings = parseTimings(timetable.get(currentIndex));
                                    for (int timing : timings) {
                                        if (timing > hour) {
                                            board_Item item = new board_Item(name, carNumber, phoneNumber, address);
                                            goSchool.add(item);
                                            break;
                                        }
                                    }
                                }
                            }

                            gs_ListAdapter adapter1 = new gs_ListAdapter(goSchool);
                            recyclerView.setAdapter(adapter1);

                            SwipeCall swipeCall = new SwipeCall(requireContext(), adapter1);
                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCall);
                            itemTouchHelper.attachToRecyclerView(recyclerView);
                        } else {
                            Toast.makeText(getContext(), "데이터 로딩 실패", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "데이터 로딩 실패", task.getException());
                        }
                    }
                });

        return rootView;
    }

    private List<Integer> parseTimings(String timings) {
        String[] timingArray = timings.split(",");
        List<Integer> timingList = new ArrayList<>();
        for (String timing : timingArray) {
            timingList.add(Integer.parseInt(timing));
        }
        return timingList;
    }
}
