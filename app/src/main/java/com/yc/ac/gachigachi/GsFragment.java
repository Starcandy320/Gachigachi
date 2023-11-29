package com.yc.ac.gachigachi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GsFragment extends Fragment {

    public GsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gs, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_gs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<board_Item> goSchool = new ArrayList<>();
        goSchool.add(new board_Item("등교 아이템 1", "차량번호 1", "010-0000-0000", "주소1", true));
        goSchool.add(new board_Item("등교 아이템 2", "차량번호 2", "010-0000-0000", "주소2", true));
        goSchool.add(new board_Item("등교 아이템 3", "차량번호 3", "010-0000-0000", "주소3", true));
        goSchool.add(new board_Item("등교 아이템 4", "차량번호 4", "010-0000-0000", "주소4", true));
        goSchool.add(new board_Item("등교 아이템 5", "차량번호 5", "010-0000-0000", "주소5", true));
        goSchool.add(new board_Item("등교 아이템 6", "차량번호 6", "010-0000-0000", "주소6", true));
        goSchool.add(new board_Item("등교 아이템 7", "차량번호 7", "010-0000-0000", "주소7", true));
        goSchool.add(new board_Item("등교 아이템 8", "차량번호 8", "010-0000-0000", "주소8", true));

        gs_ListAdapter adapter1 = new gs_ListAdapter(goSchool);

        recyclerView.setAdapter(adapter1);

        SwipeCall swipeCall = new SwipeCall(requireContext(), adapter1);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCall);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }
}
