package com.example.grocerytogo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class CekKoneksi extends BroadcastReceiver {

    String statusKoneksi;

    @Override
    public void onReceive(Context context, Intent intent) {

        try{
            if(isOnline(context)){
//                if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
//                    Toast.makeText(context, "Boot Complete", Toast.LENGTH_SHORT).show();
//                }
//                if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
//                    Toast.makeText(context, "Connectiivty Change", Toast.LENGTH_SHORT).show();
//                }
                Toast.makeText(context, "Terdapat Koneksi Internet", Toast.LENGTH_SHORT).show();
                statusKoneksi = "Terdapat Koneksi Internet....";
            }else{
                Toast.makeText(context, "Tidak Terdapat Koneksi Internet", Toast.LENGTH_SHORT).show();
                statusKoneksi = "Tidak Terdapat Koneksi Internet....";
            }
            Toast.makeText(context, statusKoneksi, Toast.LENGTH_SHORT).show();
        }catch(NullPointerException e){
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    public boolean isOnline(Context context){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return (networkInfo != null && networkInfo.isConnected());
        }catch (NullPointerException exception){
            exception.printStackTrace();
            return false;
        }
    }
}