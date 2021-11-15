package com.example.grocerytogo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditDataDiriActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText ubahNama;
    private Button simpan;
    private ImageView back, gambar;
    private AutoCompleteTextView jenkel;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_diri);

        simpan = findViewById(R.id.simpan_datadiri);
        back = findViewById(R.id.imageView5);
        gambar = findViewById(R.id.imageView6);
        jenkel = findViewById(R.id.ubah_jenkel);
        ubahNama = findViewById(R.id.ubah_nama);
        ubahNama.requestFocus();

        //Dropdown Jenkel
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jenkel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenkel.setAdapter(adapter);
        jenkel.setOnItemSelectedListener(this);

        //Ambil Gambar
        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in,REQUEST_IMAGE_CAPTURE);
            }
        });

        //Button Simpan
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Button Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

    //Pilihan Drowdown
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String pilihan = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}