package com.example.layoutdemo;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // Click listener for choosing different navigation tabs
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                // To show Linear layout demonstration
                case R.id.navigation_linear: {
                    Fragment linear_layout = LayoutDemoFragment.newInstance(LayoutDemoFragment.LINEAR_DEMO);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, linear_layout)
                            .addToBackStack(null)
                            .commit();
                    return true;
                }
                // To show Relative layout demonstration
                case R.id.navigation_relative: {
                    Fragment relative_layout = LayoutDemoFragment.newInstance(LayoutDemoFragment.RELATIVE_DEMO);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, relative_layout)
                            .addToBackStack(null)
                            .commit();
                    return true;
                }
                // To show List view demonstration
                case R.id.navigation_list: {
                    Fragment list_layout = LayoutDemoFragment.newInstance(LayoutDemoFragment.LIST_DEMO);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, list_layout)
                            .addToBackStack(null)
                            .commit();
                    return true;
                }
                // To show Recycler demonstration
                case R.id.navigation_recycler: {
                    Fragment recycler_layout = LayoutDemoFragment.newInstance(LayoutDemoFragment.RECYCLER_DEMO);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, recycler_layout)
                            .addToBackStack(null)
                            .commit();
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting for Navigation Bar
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Setting for Fragments
        Fragment linear_layout = LayoutDemoFragment.newInstance(LayoutDemoFragment.LINEAR_DEMO);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, linear_layout)
                .addToBackStack(null)
                .commit();

        ButterKnife.bind(this);
    }

}
