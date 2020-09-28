package com.example.emu.device.device;


import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;

public class DeviceIdUtil {

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {

        String deviceId;

        if (!TextUtils.isEmpty(deviceId = ITelephonyUtil.getDeviceIdLevel2(context))
                || !TextUtils.isEmpty(deviceId = IPhoneSubInfoUtil.getDeviceIdLevel2(context))) {
            return deviceId;
        }
        if (!TextUtils.isEmpty(deviceId = ITelephonyUtil.getDeviceIdLevel1(context))
                || !TextUtils.isEmpty(deviceId = IPhoneSubInfoUtil.getDeviceIdLevel1(context))) {
            return deviceId;
        }
        if (!TextUtils.isEmpty(deviceId = IPhoneSubInfoUtil.getDeviceIdLevel0(context))
                || !TextUtils.isEmpty(deviceId = ITelephonyUtil.getDeviceIdLevel0(context))) {
            return deviceId;
        }

        TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        return telephonyManager.getDeviceId();
    }


    public static boolean isEmulatorFromDeviceId(Context context) {
        return isAllZero(getDeviceId(context));
    }

    private static boolean isAllZero(@NonNull String content) {
        if (TextUtils.isEmpty(content))
            return false;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }
}