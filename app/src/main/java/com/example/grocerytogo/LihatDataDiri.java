package com.example.grocerytogo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ChangedPackages;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.VersionedPackage;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.UserHandle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LihatDataDiri extends AppCompatActivity {

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

        nope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = nope.getText().toString();
                new AlertDialog.Builder( LihatDataDiri.this)
                        .setIcon(R.mipmap.ic_launcher)
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
                                boolean installed = isAppInstalled(LihatDataDiri.this, "com.whatsapp");

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

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LihatDataDiri.this, EditDataDiri.class);
                startActivity(in);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}