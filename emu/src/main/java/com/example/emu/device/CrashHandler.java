package com.example.emu.device;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Application mApplication;

    public CrashHandler(Application application) {
        mApplication = application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Process.killProcess(Process.myPid());
        ActivityManager manager = (ActivityManager)
                mApplication.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == Process.myPid()) {
                if (!mApplication.getPackageName().equals(processInfo.processName)) {
                    Process.killProcess(Process.myPid());
                }
                break;
            }
        }

    }
}
