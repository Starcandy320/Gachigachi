package com.yc.ac.gachigachi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // 권한이 없을 경우 권한 요청
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                // 이미 권한이 부여되었을 경우 처리할 내용
                // 예: 위치 정보 가져오기
            }
        } else {
            // 안드로이드 버전이 마시멜로우 미만인 경우
            // 권한이 자동으로 부여되었으므로 처리할 내용 추가
        }
    }

    // 사용자가 권한 요청에 응답할 때 호출되는 메서드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 부여되었을 경우 처리할 내용
                // 예: 위치 정보 가져오기
            } else {
                // 권한이 거부되었을 경우 사용자에게 알림 또는 다른 처리 수행
            }
        }

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_home_fragment) {
                    replaceFragment(new HomeFragment());
                    return true;
                } else if (item.getItemId() == R.id.menu_gs_fragment) {
                    replaceFragment(new GsFragment());
                    return true;
                } else if (item.getItemId() == R.id.menu_gh_fragment) {
                    replaceFragment(new GhFragment());
                    return true;
                } else if (item.getItemId() == R.id.menu_sys_fragment) {
                    replaceFragment(new SystemFragment());
                    return true;
                }
                return false;
            }
        });


        replaceFragment(new HomeFragment());

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
