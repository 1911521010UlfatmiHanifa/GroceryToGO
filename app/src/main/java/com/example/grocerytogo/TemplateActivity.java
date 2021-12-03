package com.example.grocerytogo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TemplateActivity extends AppCompatActivity {

    private BottomNavigationView navigasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        navigasi = findViewById(R.id.navigasi);

        //Default fragmen yang ditampilkan
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();

        //Token
        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        String id = preferences.getString("ID","");
//        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

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

                //Set Fragment Yang Akan Ditampilkan
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, pilihan).commit();
                return true;
            }
        });
    }
}