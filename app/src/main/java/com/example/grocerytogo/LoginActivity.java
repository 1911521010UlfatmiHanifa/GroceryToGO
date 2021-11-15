package com.example.grocerytogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private ImageView back;
    private Button loginn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back = findViewById(R.id.back);
        loginn = findViewById(R.id.btn_login);

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
                Intent i = new Intent(LoginActivity.this, TemplateActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}