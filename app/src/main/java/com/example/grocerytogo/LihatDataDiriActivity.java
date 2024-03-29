package com.example.grocerytogo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.grocerytogo.model.UserClass;
import com.example.grocerytogo.model.UserItem;
import com.example.grocerytogo.retrofit.GtgClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LihatDataDiriActivity extends AppCompatActivity {

    private TextView nope, username,tglLahir, jenkel;
    private Button editData;
    private ImageView back, foto;
    private ScrollView sc;
    private ConstraintLayout cl;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_diri);

        nope = findViewById(R.id.lihat_nope);
        editData = findViewById(R.id.btn_editdatadiri);
        back = findViewById(R.id.imageView5);
        username = findViewById(R.id.lihat_nama);
        jenkel = findViewById(R.id.lihat_jenkel);
        tglLahir = findViewById(R.id.lihat_tgllahir);
        foto = findViewById(R.id.profil);
        sc =findViewById(R.id.sc);
        cl =findViewById(R.id.cl);
        progressBar = findViewById(R.id.progressBar5);

        progressBar.setVisibility(View.VISIBLE);
        sc.setVisibility(View.GONE);
        cl.setVisibility(View.GONE);

        getData();

        String pesan = getIntent().getStringExtra("pesan");
        if(pesan != null) {
            Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
        }
        
        //Implisit Intent Nomor HP
        nope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = nope.getText().toString();
                new AlertDialog.Builder( LihatDataDiriActivity.this)
                        .setIcon(R.drawable.nohp)
                        .setTitle("Pilihan Tindakan")
                        .setMessage("Ingin Menghubungi Melalu Apa?")
                        .setPositiveButton("Telephone", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String s = "tel:" + phone;
                                Intent in = new Intent(Intent.ACTION_CALL);
                                in.setData(Uri.parse(s));
                                startActivity(in);
                            }
                        })
                        .setNegativeButton("WHATSAPP", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                boolean installed = isAppInstalled(LihatDataDiriActivity.this, "com.whatsapp");

                                if(installed){
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phone));
                                    startActivity(i);
                                }
                            }
                        })
                        .create().show();
            }

            private boolean isAppInstalled(Context context, String s) {
                PackageManager pma = context.getPackageManager();
                List<ApplicationInfo> applicationInfos = pma.getInstalledApplications(0);
                boolean is_installed;

                try{
                    pma.getPackageArchiveInfo(s, PackageManager.GET_ACTIVITIES);
                    is_installed = true;
                } catch (Exception e) {
                    is_installed = false;
                    e.printStackTrace();
                }
                return is_installed;
            }
        });

        //Button Edit Data Diri
        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent in = new Intent(LihatDataDiriActivity.this, EditDataDiriActivity.class);
                startActivity(in);
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

    private void getData(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));

        Call<UserClass> call = gtgClient.getUser(token, id);

        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
                UserClass userClass = response.body();
                if(userClass != null){
                    progressBar.setVisibility(View.GONE);
                    sc.setVisibility(View.VISIBLE);
                    cl.setVisibility(View.VISIBLE);
                    List<UserItem> userItems = userClass.getUser();
                    for (UserItem item: userItems) {
                        Picasso.get().load(api+item.getFoto()).into(foto);
                        nope.setText(item.getNoHp());
                        jenkel.setText(item.getJenisKelamin());
                        tglLahir.setText(item.getTanggalLahir());
                        username.setText(item.getUsername());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                sc.setVisibility(View.VISIBLE);
                cl.setVisibility(View.VISIBLE);
            }
        });

    }
}