package com.yc.ac.gachigachi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

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
                SharedPreferences Save = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String savedInput = Save.getString("userInputKey", "");

                if (!savedInput.isEmpty()) {
                    showVerificationDialog(savedInput);
                } else {
                    Intent intent = new Intent(getContext(), CarReg.class);
                    startActivity(intent);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭 시 팝업창 띄우기
                showDeleteConfirmationDialog();
            }
        });
        SharedPreferences Save = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedInput = Save.getString("userInputKey","");
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
    // 차량 정보 클릭시 저장 유무 확인
    private void showVerificationDialog(final String savedInput) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_input_verification, null);
        TextInputEditText userInput = view.findViewById(R.id.userInput);

        builder.setView(view)
                .setTitle("학번 확인")
                .setMessage("본인의 학번을 입력해주세요")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = userInput.getText().toString();

                        // 저장된 값과 입력된 값이 일치할 경우 CarReg 액티비티 실행
                        if (input.equals(savedInput)) {
                            Intent intent = new Intent(getContext(), CarReg.class);
                            startActivity(intent);
                        } else {
                            // 입력 값이 일치하지 않을 경우, 다른 작업 수행 또는 사용자에게 알림 처리
                            // 여기에 추가 작업 가능
                            Toast.makeText(requireContext(), "입력한 값이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
