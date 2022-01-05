package com.example.grocerytogo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.retrofit.GtgClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahKataSandiActivity extends AppCompatActivity {

    private Button simpan;
    private ImageView back;
    private EditText pw_lama, pw_konfir, pw_baru;
    private TextView lamaKosong, baruKosong, konfirKosong;
    String pwlama, pwbaru, pwkonfir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_kata_sandi);

        simpan = findViewById(R.id.btn_ubah_sandi);
        back = findViewById(R.id.imageView7);
        pw_lama = findViewById(R.id.pw_lama);
        pw_baru = findViewById(R.id.pw_baru);
        pw_konfir = findViewById(R.id.pw_konfir);
        lamaKosong = findViewById(R.id.textView34);
        baruKosong = findViewById(R.id.textView36);
        konfirKosong = findViewById(R.id.textView37);

        //Button Simpan
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwlama = pw_lama.getText().toString();
                pwkonfir = pw_konfir.getText().toString();
                pwbaru = pw_baru.getText().toString();

                if(pwlama.length() < 8){
                    lamaKosong.setVisibility(View.VISIBLE);
                    lamaKosong.setText("Masukkan Kata Sandi Lama Minimal 8 Karakter");
                }
                if(pwbaru.length() < 8){
                    baruKosong.setVisibility(View.VISIBLE);
                    baruKosong.setText("Masukkan Kata Sandi Baru Minimal 8 Karakter");
                }
                if(pwkonfir.length() < 8){
                    konfirKosong.setVisibility(View.VISIBLE);
                    konfirKosong.setText("Masukkan Kata Sandi Konfirmasi Minimal 8 Karakter");
                }
                if(pwlama.length() >= 8 && pwbaru.length() >= 8 && pwkonfir.length() >= 8) {
                    lamaKosong.setVisibility(View.GONE);
                    baruKosong.setVisibility(View.GONE);
                    konfirKosong.setVisibility(View.GONE);
                    ubahKataSandi();
                }
            }
        });

        //Image Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void ubahKataSandi(){
        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo", MODE_PRIVATE);
        String token = preferences.getString("TOKEN", "");
        Integer id = Integer.valueOf(preferences.getString("id", ""));
        String pwOpor = preferences.getString("PASSWORD", "");
//        Toast.makeText(getApplicationContext(), pwOpor, Toast.LENGTH_SHORT).show();
        if(pwOpor.equals(pwlama)) {
            if (pwkonfir.equals(pwbaru)) {
                String api = getString(R.string.apiGTG);
                Koneksi koneksi = new Koneksi();
                GtgClient gtgClient = koneksi.setGtgClient(api);

                Call<Pesan> call = gtgClient.ubahSandi(token, pwlama, pwbaru, id);
                call.enqueue(new Callback<Pesan>() {
                    @Override
                    public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                        Pesan p = response.body();
                        if (p != null) {
                            Toast.makeText(getApplicationContext(), "Kata Sandi Berhasil Diubah", Toast.LENGTH_SHORT).show();
                            pw_baru.setText("");
                            pw_lama.setText("");
                            pw_konfir.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Kata Sandi Gagal Diubah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Pesan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal Ubah Kata Sandi", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Kata Sandi Baru dan Konfirmasi Tidak Cocok", Toast.LENGTH_SHORT).show();
                pw_baru.setText("");
                pw_lama.setText("");
                pw_konfir.setText("");
            }
        }else{
            Toast.makeText(getApplicationContext(), "Kata Sandi Lama Tidak Sesuai", Toast.LENGTH_SHORT).show();
            pw_baru.setText("");
            pw_lama.setText("");
            pw_konfir.setText("");
        }
    }
}