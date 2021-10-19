package com.example.grocerytogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TemplateActivity extends AppCompatActivity {

    private BottomNavigationView navigasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        navigasi = findViewById(R.id.navigasi);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();

        navigasi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment pilihan = null;

                switch (item.getItemId()) {
                    case R.id.action_home:
                        pilihan = new HomeFragment();
                        break;
                    case R.id.action_keranjang:
                        pilihan = new KeranjangSayaFragment();
                        break;
                    case R.id.action_settings:
                        pilihan = new SettingFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, pilihan).commit();
                return true;
            }
        });
    }
}