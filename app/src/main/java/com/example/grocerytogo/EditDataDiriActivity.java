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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.model.UserClass;
import com.example.grocerytogo.model.UserItem;
import com.example.grocerytogo.retrofit.GtgClient;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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
    private TextView nopeKosong, tglKosong, jkKosong;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String tglLahir, pilihan;
    String foto, fotoLama;
    String nope, tgl, jk, tahunSekarang;
    int a;

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
        jkKosong = findViewById(R.id.textView30);
        nopeKosong = findViewById(R.id.textView32);
        tglKosong = findViewById(R.id.textView33);

        Calendar calender = Calendar.getInstance();
        final int tahun = calender.get(Calendar.YEAR);
        final int bulan = calender.get(Calendar.MONTH);
        final int tanggal = calender.get(Calendar.DAY_OF_MONTH);

//        SimpleDateFormat df = new SimpleDateFormat("yyyy");
//        tahunSekarang = df.format(calender.getTime());
//        Toast.makeText(getApplicationContext(), (Integer.parseInt(tahunSekarang)-tahun), Toast.LENGTH_SHORT).show();

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
//        Toast.makeText(getApplicationContext(), String.valueOf(umur), Toast.LENGTH_SHORT).show();

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
//                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(in,REQUEST_IMAGE_CAPTURE);
                Intent intent = new Intent(EditDataDiriActivity.this, AvatarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Button Simpan
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nope = ubahNo.getText().toString();
                tgl = ubahTgl.getText().toString();
                jk = jenkel.getText().toString();

                if(nope.isEmpty()){
                    nopeKosong.setVisibility(View.VISIBLE);
                    nopeKosong.setText("Masukkan Nomor HP");
                }
                if(tgl.isEmpty()){
                    tglKosong.setVisibility(View.VISIBLE);
                    tglKosong.setText("Masukkan Tanggal Lahir");
                }
                if(jk.isEmpty()){
                    jkKosong.setVisibility(View.VISIBLE);
                    jkKosong.setText("Masukkan Jenis Kelamin");
                }
                if(nope.length() > 0 && tgl.length() > 0 && jk.length() > 0) {
                    nopeKosong.setVisibility(View.GONE);
                    tglKosong.setVisibility(View.GONE);
                    jkKosong.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), ubahTgl.getText().toString(), Toast.LENGTH_SHORT).show();
                    long umur = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        LocalDate now = LocalDate.now();
                        umur = Period.between(LocalDate.parse(ubahTgl.getText().toString()), now).getYears();
                        if(umur >=17){
                            editData();
                        }else{
                            Toast.makeText(getApplicationContext(), "Umur Belum Mencukupi", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        //Button Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditDataDiriActivity.this, LihatDataDiriActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();
//            Bitmap image = (Bitmap) extras.get("data");
//            gambar.setImageBitmap(image);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void editData(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));
        foto = getIntent().getStringExtra("GR");
        String updateFoto;
        if(foto == null){
            updateFoto = fotoLama;
        }else{
            updateFoto = foto;
        }

        Call<Pesan> call = gtgClient.editDataDiri(token, tgl, jk, nope, id, updateFoto);
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
                UserClass userClass = response.body();
                if(userClass != null){
                    List<UserItem> userItems = userClass.getUser();
                    for (UserItem item: userItems) {
                        foto = getIntent().getStringExtra("GR");
//                        Toast.makeText(getApplicationContext(), foto, Toast.LENGTH_SHORT).show();
                        if(foto == null){
                            Picasso.get().load(api+item.getFoto()).into(gambar);
                        }else if(foto==item.getFoto()){
                            Picasso.get().load(api+item.getFoto()).into(gambar);
                        }else{
                            Picasso.get().load(api+foto).into(gambar);
                        }
                        fotoLama = item.getFoto();
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