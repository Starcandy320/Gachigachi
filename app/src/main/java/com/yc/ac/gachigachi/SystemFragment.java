package com.yc.ac.gachigachi;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SystemFragment extends Fragment {

    private TextView delete, carReg;
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_system, container, false);

        delete = rootView.findViewById(R.id.delete);
        carReg = rootView.findViewById(R.id.carReg);
        SharedPreferences Save = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedInput = Save.getString("userInputKey", "");
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
        SharedPreferences Save = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedInput = Save.getString("userInputKey", "");

        SharedPreferences.Editor editor = Save.edit();
        editor.remove("userInputKey");
        editor.apply();

        // savedInput 값이 있다면 Firebase에서 해당 studentID를 가진 문서를 삭제합니다.
        if (!savedInput.isEmpty()) {
            db.collection("carList")
                    .whereEqualTo("studentID", savedInput)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                // 문서 삭제
                                db.collection("carList")
                                        .document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(requireContext(), "정상적으로 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(requireContext(), "삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });
        } else{
            Toast.makeText(requireContext(), "등록된 사용자가 아닙니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
