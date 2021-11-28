package com.example.grocerytogo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LihatDataDiriActivity extends AppCompatActivity {

    private TextView nope;
    private Button editData;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_diri);

        nope = findViewById(R.id.lihat_nope);
        editData = findViewById(R.id.btn_editdatadiri);
        back = findViewById(R.id.imageView5);

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

        //Button Edit AuthData Diri
        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}