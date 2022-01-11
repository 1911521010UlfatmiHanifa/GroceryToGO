package com.example.grocerytogo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    ImageView back;
    Button daftar;
    TextView textUsername, textPassword, textNope, userKosong, pwKosong, nopeKosong;
    ProgressBar progressBar;
    String username, password, no_hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        back = findViewById(R.id.image_backDaftar);
        daftar = findViewById(R.id.btn_daftar);
        progressBar = findViewById(R.id.progressBar7);
        userKosong = findViewById(R.id.textView28);
        nopeKosong = findViewById(R.id.textView29);
        pwKosong = findViewById(R.id.textView31);
        textUsername = findViewById(R.id.daftar_user);
        textPassword = findViewById(R.id.daftar_password);
        textNope = findViewById(R.id.daftar_nohp);

        textUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    userKosong.setVisibility(View.VISIBLE);
                    userKosong.setText("Masukkan Username");
                }else{
                    userKosong.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textNope.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 3 && (s.subSequence(0,3).toString().equals("+62") || s.subSequence(0,2).toString().equals("62") || s.subSequence(0,1).toString().equals("0"))){
                    nopeKosong.setVisibility(View.GONE);
                }else{
                    nopeKosong.setVisibility(View.VISIBLE);
                    nopeKosong.setText("Masukkan Nomor HP Dengan Benar");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 8) {
                    pwKosong.setVisibility(View.VISIBLE);
                    pwKosong.setText("Masukkan Password Minimal 8 Karakter");
                }else{
                    pwKosong.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        daftar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(DaftarActvity.this, MainActivity.class);
                startActivity(i);
            }
        });

        daftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                username = textUsername.getText().toString();
                password = textPassword.getText().toString();
                no_hp = textNope.getText().toString();

                if(username.isEmpty()){
                    userKosong.setVisibility(View.VISIBLE);
                    userKosong.setText("Masukkan Username");
                }
                if(password.length() < 8){
                    pwKosong.setVisibility(View.VISIBLE);
                    pwKosong.setText("Password Kurang Dari 8 Karakter");
                }
                if(no_hp.isEmpty()){
                    nopeKosong.setVisibility(View.VISIBLE);
                    nopeKosong.setText("Masukkan Nomor HP");
                }
                if(username.length() > 0 && password.length() >= 8 && no_hp.length() > 0){
                    userKosong.setVisibility(View.GONE);
                    pwKosong.setVisibility(View.GONE);
                    nopeKosong.setVisibility(View.GONE);
                    registert();
                }
            }
        });
    }

    private void registert(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        daftar.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<Pesan> call = gtgClient.register(username, password, no_hp);
        call.enqueue(new Callback<Pesan>() {
            @Override
            public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                Pesan p = response.body();
                if(p != null) {
                    String pesan = "Pendaftaran Berhasil Silahkan Login";
                    Intent i = new Intent(DaftarActvity.this, LoginActivity.class);
                    i.putExtra("pesan", pesan);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Pendaftaran Gagal", Toast.LENGTH_SHORT).show();
                    daftar.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Pesan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Mengakses Akses", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}