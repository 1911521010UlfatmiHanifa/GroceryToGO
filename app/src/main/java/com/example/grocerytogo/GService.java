package com.example.grocerytogo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class GService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "com.example.grocerytogo.CH01";
    Intent intent;
    String idTransaksi;
    PendingIntent pendingIntent;
    String status;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        idTransaksi = remoteMessage.getData().get("id_transaksi");
        status = remoteMessage.getData().get("status_transaksi");

        displayNotification(title,message);
    }

    private void displayNotification(String title, String message){
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel Notifikasi Transaksi",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        intent = new Intent(getApplicationContext(),LihatDetailPesananActivity.class);
        intent.putExtra("id", idTransaksi);
        intent.putExtra("status", status);


        pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                12345,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notif)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .addAction(
                    R.drawable.notif,
                    "CLICK ME",
                    pendingIntent
                )
                .build();

        notificationManager.notify(123,notification);
    }
}