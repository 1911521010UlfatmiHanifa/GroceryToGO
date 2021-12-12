package com.example.grocerytogo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.model.UserClass;
import com.example.grocerytogo.model.UserItem;
import com.example.grocerytogo.retrofit.GtgClient;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDataDiriActivity extends AppCompatActivity{

    private EditText ubahNama, ubahTgl, ubahNo;
    private Button simpan;
    private ImageView back, gambar;
    private AutoCompleteTextView jenkel;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String tglLahir, pilihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_diri);

        simpan = findViewById(R.id.simpan_datadiri);
        back = findViewById(R.id.imageView5);
        gambar = findViewById(R.id.imageView6);
        jenkel = findViewById(R.id.ubah_jenkel);
        ubahNama = findViewById(R.id.ubah_nama);
        ubahTgl = findViewById(R.id.ubah_tgllahir);
        ubahNo = findViewById(R.id.ubah_nohp);
        ubahNo.requestFocus();
        ubahNama.setEnabled(false);

        Calendar calender = Calendar.getInstance();
        final int tahun = calender.get(Calendar.YEAR);
        final int bulan = calender.get(Calendar.MONTH);
        final int tanggal = calender.get(Calendar.DAY_OF_MONTH);

        ubahTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditDataDiriActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        tglLahir = year + "-" + month + "-" + dayOfMonth;
                        ubahTgl.setText(tglLahir);
                    }
                }, tahun, bulan, tanggal);
                datePickerDialog.show();
            }
        });

        getData();

        //Dropdown Jenkel
        String [] jnkl = {"Laki-laki", "Perempuan"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jenkel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenkel.setAdapter(adapter);

        jenkel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pilihan = parent.getItemAtPosition(position).toString();
            }
        });

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
                editData();
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

    private void editData(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));
        String nope = ubahNo.getText().toString();
        String tgl = ubahTgl.getText().toString();
        String jk = jenkel.getText().toString();

        Call<Pesan> call = gtgClient.editDataDiri(token, tgl, jk, nope, id);
        call.enqueue(new Callback<Pesan>() {
            @Override
            public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                Pesan p = response.body();
                if(p != null) {
                    String pesan = "Edit Data Diri Berhasil";
                    finish();
                    Intent i = new Intent(EditDataDiriActivity.this, LihatDataDiriActivity.class);
                    i.putExtra("pesan", pesan);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Edit data diri Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pesan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Akses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));
//        Toast.makeText(getApplicationContext(), id.toString(), Toast.LENGTH_SHORT).show();

        Call<UserClass> call = gtgClient.getUser(token, id);

        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
//                Toast.makeText(getApplicationContext(), "B", Toast.LENGTH_SHORT).show();
                UserClass userClass = response.body();
                if(userClass != null){
                    List<UserItem> userItems = userClass.getUser();
                    for (UserItem item: userItems) {
                        Picasso.get().load(api+item.getFoto()).into(gambar);
                        ubahNo.setText(item.getNoHp());
                        jenkel.setText(item.getJenisKelamin());
                        ubahTgl.setText(item.getTanggalLahir());
                        ubahNama.setText(item.getUsername());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}