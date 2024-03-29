package com.example.grocerytogo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerytogo.model.AuthClass;
import com.example.grocerytogo.model.ListUserCek;
import com.example.grocerytogo.model.UserCekItem;
import com.example.grocerytogo.retrofit.GtgClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView daftar, daftar2;
    Button login;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daftar = findViewById(R.id.textView2);
        daftar2 = findViewById(R.id.textView19);
        login = findViewById(R.id.btn_masuk);

        //Text View Daftar
        daftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, DaftarActvity.class);
                startActivity(i);
            }
        });

        daftar2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent a = new Intent(MainActivity.this, DaftarActvity.class);
                startActivity(a);
            }
        });

        //Button Masuk
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent j = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(j);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        broadcastReceiver = new CekKoneksi();
        registerNetwork();

        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo", MODE_PRIVATE);
        String token = preferences.getString("TOKEN", "");
        String fcm = preferences.getString("FCM", "");
        if (token != "" && fcm != "") {
            String api = getString(R.string.apiGTG);
            Koneksi koneksi = new Koneksi();
            GtgClient gtgClient = koneksi.setGtgClient(api);

            Call<ListUserCek> call = gtgClient.cekUser(token, fcm);
            call.enqueue(new Callback<ListUserCek>() {
                @Override
                public void onResponse(Call<ListUserCek> call, Response<ListUserCek> response) {
                    ListUserCek listUserCek = response.body();
                    List<UserCekItem> userCekItem = listUserCek.getUserCek();
                    if (userCekItem.size() > 0) {
                        Intent j = new Intent(MainActivity.this, TemplateActivity.class);
                        startActivity(j);
                    }
                }

                @Override
                public void onFailure(Call<ListUserCek> call, Throwable t) {

                }
            });
        }
    }

    protected void registerNetwork(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        }
    }

    protected void unregisterNetwork(){
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetwork();
    }
}