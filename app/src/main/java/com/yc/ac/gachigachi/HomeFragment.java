package com.yc.ac.gachigachi;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.TooltipCompat;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.MultiBrowseCarouselStrategy;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private static final String SWITCH_STATE_KEY = "switch_state";
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences preferences;

    private MaterialTextView additionalTextView;
    private LinearLayout optionsLayout;
    private  MaterialButton btnWrite;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_home);
        recyclerView.setLayoutManager(new CarouselLayoutManager(new MultiBrowseCarouselStrategy()));
        recyclerView.setAdapter(new RecyclerViewAdapter());

        MaterialButton btnHome = rootView.findViewById(R.id.btnHome);
        MaterialButton btnNotice = rootView.findViewById(R.id.btnNotice);
        MaterialButton btnCalendar = rootView.findViewById(R.id.btnCalendar);
        MaterialButton btnLibrary = rootView.findViewById(R.id.btnLibrary);
        MaterialButton btnOption1 = rootView.findViewById(R.id.btnOption1);
        MaterialButton btnOption2 = rootView.findViewById(R.id.btnOption2);
        MaterialButton btnOption3 = rootView.findViewById(R.id.btnOption3);
        SwitchMaterial switchButton = rootView.findViewById(R.id.switchBtn);

        optionsLayout = rootView.findViewById(R.id.optionsLayout);
        additionalTextView = rootView.findViewById(R.id.additionalTextView);
        btnWrite = rootView.findViewById(R.id.btnWrite);

        btnHome.setOnClickListener(v -> openChromeCustomTab("https://www.yc.ac.kr/yonam/web/main/mainPage.do"));

        btnNotice.setOnClickListener(v -> openChromeCustomTab("https://www.yc.ac.kr/yonam/web/cop/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000590"));

        btnCalendar.setOnClickListener(v -> openChromeCustomTab("https://www.yc.ac.kr/yonam/web/content.do?proFn=44100000"));

        btnLibrary.setOnClickListener(v -> openChromeCustomTab("http://ycc4.yc.ac.kr/Cheetah/YONAM/Index/"));

        btnWrite.setOnClickListener(v -> toggleAdditionalTextView());

        btnOption1.setOnClickListener(v -> additionalTextView.setText(getString(R.string.carpool1)));
        btnOption2.setOnClickListener(v -> additionalTextView.setText(getString(R.string.carpool2)));
        btnOption3.setOnClickListener(v -> additionalTextView.setText(getString(R.string.carpool3)));
        SharedPreferences Save = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedInput = Save.getString("userInputKey","");

        boolean switchState = preferences.getBoolean(SWITCH_STATE_KEY, true);
        switchButton.setChecked(switchState);

        if (savedInput.isEmpty()) {
            switchButton.setVisibility(View.GONE);

        } else {
            switchButton.setVisibility(View.VISIBLE);

            TooltipCompat.setTooltipText(switchButton, switchState ? "카풀 활성화" : "카풀 비활성화");
            switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {

                boolean isShow;
                isShow = isChecked;

                db.collection("carList")
                        .whereEqualTo("studentID", savedInput)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    db.collection("carList")
                                            .document(document.getId())
                                            .update("isShow", isShow)
                                            .addOnSuccessListener(aVoid -> Log.d(TAG, "업데이트 성공"))
                                            .addOnFailureListener(e -> Log.w(TAG, "업데이트 실패", e));
                                    showTooltip(switchButton, isChecked);
                                    preferences.edit().putBoolean(SWITCH_STATE_KEY, isChecked).apply();
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        });
            });
        }

        return rootView;
    }

    private void showTooltip(View view, boolean isChecked) {
        String message = isChecked ? "카풀 활성화" : "카풀 비활성화";
        TooltipCompat.setTooltipText(view, message);

        new Handler(Looper.getMainLooper()).postDelayed(() -> TooltipCompat.setTooltipText(view, message), 3000);

    }

    private void toggleAdditionalTextView() {
        if (additionalTextView.getVisibility() == View.VISIBLE) {
            additionalTextView.setVisibility(View.GONE);
            optionsLayout.setVisibility(View.GONE);
            btnWrite.setIconResource(R.drawable.more);
        } else {
            additionalTextView.setVisibility(View.VISIBLE);
            optionsLayout.setVisibility(View.VISIBLE);
            btnWrite.setIconResource(R.drawable.less);
        }
    }

    private void openChromeCustomTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url));
    }
    public static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass> {

        private final String[] textArray = new String[]{ "차량등록하러가기", "등교", "하교" };

        public static class ViewHolderClass extends RecyclerView.ViewHolder {
            private final TextView carouselTextView;
            private final TextView carouselTextView2;
            private final ImageView carouselImageView;
            private final TextView carouselName;
            private final TextView carouselPhoneNum;
            private final TextView carouselTime;
            private final CardView cardView;

            public ViewHolderClass(View itemView) {
                super(itemView);
                carouselTextView = itemView.findViewById(R.id.carousel_text_view);
                carouselTextView2 = itemView.findViewById(R.id.carousel_text_view2);
                carouselName = itemView.findViewById(R.id.carousel_name);
                carouselTime = itemView.findViewById(R.id.carousel_time);
                carouselPhoneNum = itemView.findViewById(R.id.carousel_phoneNum);
                carouselImageView = itemView.findViewById(R.id.carousel_image_view);
                cardView = itemView.findViewById(R.id.carousel_cardview);

                itemView.setOnClickListener(v -> {
                    if (getAdapterPosition() == 0) {
                        Intent intent = new Intent(v.getContext(), CarReg.class);
                        v.getContext().startActivity(intent);
                    }

                });
            }
        }

        @NonNull
        @Override
        public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_carousel, parent, false);
            return new ViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

            holder.carouselTextView.setText(textArray[position]);

            if (position == 0) {
                holder.carouselImageView.setImageResource(R.drawable.car);
                holder.carouselTextView2.setText("차량이 있으시다면 등록하고\n카풀운전자가 되어보세요!");
            }
            if (position == 1) {
                holder.carouselImageView.setImageResource(R.drawable.assignment);
                holder.carouselTime.setText(getDay());
                holder.cardView.setVisibility(View.VISIBLE);
                loadRandomFirebaseData(holder);
            }
            if (position == 2){
                holder.carouselImageView.setImageResource(R.drawable.assignment);
                holder.carouselTime.setText(getDay());
                holder.cardView.setVisibility(View.VISIBLE);
                loadRandomFirebaseData(holder);
            }
        }

        private String getDay() {
            SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일 (EEE)", Locale.getDefault());
            return sdf.format(new Date());
        }

        private void loadRandomFirebaseData(ViewHolderClass holder) {
            db.collection("carList")
                    .whereEqualTo("isShow", true)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        int size = queryDocumentSnapshots.size();
                        if (size > 0) {
                            int randomIndex = (int) (Math.random() * size);
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(randomIndex);

                            String firebaseName = documentSnapshot.getString("name");
                            String firebasePhoneNumber = documentSnapshot.getString("phoneNumber");

                            holder.carouselName.setText(firebaseName);
                            holder.carouselPhoneNum.setText(firebasePhoneNumber);
                        }
                    })
                    .addOnFailureListener(e -> Log.e(TAG, "데이터 로딩 실패", e));
        }

        @Override
        public int getItemCount() {
            return textArray.length;
        }
    }
}
