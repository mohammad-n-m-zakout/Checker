package com.example.emu.jni;

import android.content.Context;

import com.example.emu.device.AndroidDeviceIMEIUtil;

public class EmulatorDetectUtil {

    static {
        System.loadLibrary("emulator_check");
    }

    public static native boolean detect();

    public static boolean isEmulator(Context context) {
        return AndroidDeviceIMEIUtil.isRunOnEmulator(context) || detect();
    }

    public static boolean isEmulator() {
        return detect();
    }

    public void throwNativeCrash() {

    }
}
