package com.example.grocerytogo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EditDataDiri extends AppCompatActivity {

    private Button simpan;
    private ImageView back, gambar;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_diri);

        simpan = findViewById(R.id.simpan_datadiri);
        back = findViewById(R.id.imageView5);
        gambar = findViewById(R.id.imageView6);

        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in,REQUEST_IMAGE_CAPTURE);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(EditDataDiri.this, Pengaturan.class);
                startActivity(in);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(EditDataDiri.this, LihatDataDiri.class);
                startActivity(in);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            gambar.setImageBitmap(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}