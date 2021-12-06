package com.example.grocerytogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerytogo.model.AuthClass;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.retrofit.GtgClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaftarActvity extends AppCompatActivity {

    private ImageView back;
    private Button daftar;
    private TextView textUsername, textPassword, textNope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        back = findViewById(R.id.image_backDaftar);
        daftar = findViewById(R.id.btn_daftar);

        //Image Back
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(DaftarActvity.this, MainActivity.class);
                startActivity(i);
            }
        });

        //Button Selanjutnya
        daftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registert();
            }
        });
    }

    private void registert(){

        textUsername = findViewById(R.id.daftar_user);
        textPassword = findViewById(R.id.daftar_password);
        textNope = findViewById(R.id.daftar_nohp);

        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        String no_hp = textNope.getText().toString();

        String API_BASE_URL = "https://groceriestogo1208.herokuapp.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GtgClient gtgClient = retrofit.create(GtgClient.class);
        Call<Pesan> call = gtgClient.register(username, password, no_hp);
        call.enqueue(new Callback<Pesan>() {
            @Override
            public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                Pesan p = response.body();
                if(p != null) {
                    Intent i = new Intent(DaftarActvity.this, HomeFragment.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Pendaftaran Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pesan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Akses", Toast.LENGTH_SHORT).show();
            }
        });
    }
}