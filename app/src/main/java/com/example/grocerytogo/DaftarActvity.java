package com.example.grocerytogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DaftarActvity extends AppCompatActivity {

    private ImageView back;
    private Button daftar;

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
                Intent i = new Intent(DaftarActvity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}