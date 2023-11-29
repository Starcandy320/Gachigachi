package com.yc.ac.gachigachi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SystemFragment extends Fragment {

    private TextView delete, carReg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_system, container, false);

        delete = rootView.findViewById(R.id.delete);
        carReg = rootView.findViewById(R.id.carReg);
        carReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CarReg.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭 시 팝업창 띄우기
                showDeleteConfirmationDialog();
            }
        });

        return rootView;
    }

    private void showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle("차량 정보 삭제")
                .setMessage("정말로 차량 정보를 삭제하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // "예" 버튼이 클릭된 경우 데이터 삭제 작업 수행
                        performDeleteOperation();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // "아니오" 버튼 클릭 시 처리할 내용 추가 가능
                    }
                })
                .show();
    }

    private void performDeleteOperation() {
        // 데이터 삭제 작업을 수행 하는 코드를 여기에 추가
    }
}
