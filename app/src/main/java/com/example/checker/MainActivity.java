package com.example.checker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.snail.antifake.jni.EmulatorDetectUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if (EmulatorDetectUtil.isEmulator(this)) {
                Toast.makeText(this, "This is emulator", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "This is normal Device ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("TAG", "onCreate: ", e);
        }

    }
}