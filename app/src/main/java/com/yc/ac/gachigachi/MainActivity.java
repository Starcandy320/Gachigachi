package com.yc.ac.gachigachi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
