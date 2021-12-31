package com.example.grocerytogo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerytogo.model.AuthClass;
import com.example.grocerytogo.model.AuthData;
import com.example.grocerytogo.retrofit.GtgClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextView textUsername, textPassword, userKosong, pwKosong;
    private ImageView back;
    private Button loginn;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back = findViewById(R.id.back);
        loginn = findViewById(R.id.btn_login);
        userKosong = findViewById(R.id.textView22);
        pwKosong = findViewById(R.id.textView21);

        String pesan = getIntent().getStringExtra("pesan");
        if(pesan != null) {
            Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
        }

        //Image Back
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        //Button Selanjutnya
        loginn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textUsername = findViewById(R.id.nama_pengguna);
                textPassword = findViewById(R.id.password);

                username = textUsername.getText().toString();
                password = textPassword.getText().toString();
                if(password.length() < 8) {
                    pwKosong.setVisibility(View.VISIBLE);
                    pwKosong.setText("Masukkan Password Minimal 8 Karakter");
                }
                if(username.isEmpty()){
                    userKosong.setVisibility(View.VISIBLE);
                    userKosong.setText("Masukkan Username");
                }
                if(username.length() > 0 && password.length() >= 8) {
                    checkLogin();
                }
            }
        });


    }

    private void checkLogin(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        pwKosong.setVisibility(View.GONE);
        userKosong.setVisibility(View.GONE);
        Call<AuthClass> call = gtgClient.checkLogin(username, password);
        updateViewProgress(true);
        call.enqueue(new Callback<AuthClass>() {
            @Override
            public void onResponse(Call<AuthClass> call, Response<AuthClass> response) {
                AuthClass authClass = response.body();
                if (authClass != null) {
                    AuthData authData = authClass.getAuthData();
                    String accesToken = authData.getToken();
                    Integer id = authData.getId();

//                    Toast.makeText(getApplicationContext(), accesToken, Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("TOKEN", accesToken);
                    editor.putString("id", id.toString());
                    editor.apply();

                    Intent i = new Intent(LoginActivity.this, TemplateActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Username dan Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                    updateViewProgress(false);
                    textUsername.setText("");
                    textPassword.setText("");
                }
            }

            @Override
            public void onFailure(Call<AuthClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
                updateViewProgress(false);
                textUsername.setText("");
                textPassword.setText("");
            }
        });
    }

    public void updateViewProgress(Boolean active){
        loginn = findViewById(R.id.btn_login);
        textUsername = findViewById(R.id.nama_pengguna);
        textPassword = findViewById(R.id.password);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        if (active){
            textUsername.setEnabled(false);
            textPassword.setEnabled(false);
            loginn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            textUsername.setEnabled(true);
            textPassword.setEnabled(true);
            loginn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}