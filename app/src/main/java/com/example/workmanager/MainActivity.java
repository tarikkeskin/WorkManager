package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.workmanager.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding tasarim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasarim = ActivityMainBinding.inflate(getLayoutInflater());


        setContentView(tasarim.getRoot());


        tasarim.buttonYap.setOnClickListener(view -> {
            Constraints calismaKosulu = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            WorkRequest istek = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInitialDelay(6, TimeUnit.SECONDS)
                    .setConstraints(calismaKosulu)
                    .build();

            WorkManager.getInstance(this).enqueue(istek);

            WorkManager.getInstance(this).getWorkInfoByIdLiveData(istek.getId())
                    .observe(this,workInfo -> {
                        String durum = workInfo.getState().name();
                        Log.e("Arkaplan işlem durumu",durum);
                    });

        });
    }
}