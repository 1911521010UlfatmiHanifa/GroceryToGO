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
//              register();
                Intent i = new Intent(DaftarActvity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void register(){

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
        Call<AuthClass> call = gtgClient.register(username, no_hp, password);
        call.enqueue(new Callback<AuthClass>() {
            @Override
            public void onResponse(Call<AuthClass> call, Response<AuthClass> response) {
                AuthClass authClass = response.body();
                if (authClass != null){
//                    AuthData authData = authClass.getData();
//                    String accesToken = authData.getToken();
                    Toast.makeText(getApplicationContext(), "Bisa", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(DaftarActvity.this, LoginActivity.class);
//                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Field Masih Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}