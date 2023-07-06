package com.dawn.libdelay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.dawn.delay.DelayFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DelayFactory.getInstance(this).setListener((signal, delay) -> {
            runOnUiThread(() -> {
                Log.i("dawn", "signal:" + signal + " delay:" + delay);
            });
        });
        DelayFactory.getInstance(this).cycleDelay();
    }
}