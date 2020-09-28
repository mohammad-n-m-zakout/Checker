package com.example.emu.jni;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.emu.IEmulatorCheck;

public class EmulatorCheckService extends Service {

    private static final String TAG = EmulatorCheckService.class.getSimpleName();
    Handler mHandler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new IEmulatorCheck.Stub() {
            @Override
            public boolean isEmulator() throws RemoteException {
                return EmulatorDetectUtil.isEmulator(EmulatorCheckService.this);

            }

            @Override
            public void kill() throws RemoteException {
                stopSelf();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                }, 500);
            }
        };

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate");
    }
}
