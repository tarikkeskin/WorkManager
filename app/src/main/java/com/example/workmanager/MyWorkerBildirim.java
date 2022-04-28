package com.example.workmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorkerBildirim extends Worker {
    public MyWorkerBildirim(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        bildirimGoster();
        return Result.success();
    }

    private void bildirimGoster() {
        NotificationCompat.Builder builder;
        NotificationManager bildirimYoneticisi =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent gidilicekIntent =
                PendingIntent.getActivity(getApplicationContext(),
                        1,intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String kanalId = "kanalId";
            String kanalAd = "kanalAd";
            String kanalTanım = "kanalTanım";
            int kanalOnceligi = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel kanal = bildirimYoneticisi.getNotificationChannel(kanalId);

            if(kanal == null){
                kanal = new NotificationChannel(kanalId,kanalAd,kanalOnceligi);
                kanal.setDescription(kanalTanım);
                bildirimYoneticisi.createNotificationChannel(kanal);
            }

            builder = new NotificationCompat.Builder(getApplicationContext(),kanalId);
            builder.setContentTitle("Başlık")
                    .setContentText("İçerik")
                    .setContentIntent(gidilicekIntent)
                    .setSmallIcon(R.drawable.resim)
                    .setAutoCancel(true);

        }else{
            builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setContentTitle("Başlık")
                    .setContentText("İçerik")
                    .setContentIntent(gidilicekIntent)
                    .setSmallIcon(R.drawable.resim)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        bildirimYoneticisi.notify(1,builder.build());
    }
}
