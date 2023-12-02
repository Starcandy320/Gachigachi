package com.yc.ac.gachigachi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.MultiBrowseCarouselStrategy;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new CarouselLayoutManager(new MultiBrowseCarouselStrategy()));
        recyclerView.setAdapter(new RecyclerViewAdapter());

        MaterialButton btnHome = rootView.findViewById(R.id.btnHome);
        MaterialButton btnNotice = rootView.findViewById(R.id.btnNotice);
        MaterialButton btnCalendar = rootView.findViewById(R.id.btnCalendar);
        MaterialButton btnLibrary = rootView.findViewById(R.id.btnLibrary);

        btnHome.setOnClickListener(v -> openChromeCustomTab("https://www.yc.ac.kr/yonam/web/main/mainPage.do"));

        btnNotice.setOnClickListener(v -> openChromeCustomTab("https://www.yc.ac.kr/yonam/web/cop/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000590"));

        btnCalendar.setOnClickListener(v -> openChromeCustomTab("https://www.yc.ac.kr/yonam/web/content.do?proFn=44100000"));

        btnLibrary.setOnClickListener(v -> openChromeCustomTab("http://ycc4.yc.ac.kr/Cheetah/YONAM/Index/"));

        return rootView;
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

            public ViewHolderClass(View itemView) {
                super(itemView);
                carouselTextView = itemView.findViewById(R.id.carousel_text_view);
                carouselTextView2 = itemView.findViewById(R.id.carousel_text_view2);
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
                holder.carouselTextView2.setText("차량이 있으시다면 등록하고\n카풀운전자가 되어보세요!");
            }
        }

        @Override
        public int getItemCount() {
            return textArray.length;
        }
    }
}
