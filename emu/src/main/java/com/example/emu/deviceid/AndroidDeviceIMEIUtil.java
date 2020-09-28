package com.example.emu.deviceid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.emu.deviceid.androidid.IAndroidIdUtil;
import com.example.emu.deviceid.deviceid.DeviceIdUtil;
import com.example.emu.deviceid.emulator.EmuCheckUtil;
import com.example.emu.deviceid.macaddress.MacAddressUtils;
import com.example.emu.jni.PropertiesGet;

public class AndroidDeviceIMEIUtil {

    public static boolean isRunOnEmulator(Context context) {
        return EmuCheckUtil.mayOnEmulator(context);

    }

    public static String getDeviceId(Context context) {
        return DeviceIdUtil.getDeviceId(context);

    }

    public static String getAndroidId(Context context) {

        return IAndroidIdUtil.getAndroidId(context);
    }

    public static String getMacAddress(Context context) {

        return MacAddressUtils.getMacAddress(context);
    }


    @SuppressLint("MissingPermission")
    public static String getSerialNo() {
        String serialNo = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serialNo = Build.getSerial();
            } else {
                serialNo = PropertiesGet.getString("ro.serialno");
                if (TextUtils.isEmpty(serialNo)) {
                    serialNo = Build.SERIAL;
                }
            }
        } catch (Exception e) {
        }
        return serialNo;
    }


    public static String getManufacturer() {
        return PropertiesGet.getString("ro.product.manufacturer");
    }


    public static String getBrand() {
        return PropertiesGet.getString("ro.product.brand");
    }

    public static String getModel() {
        return PropertiesGet.getString("ro.product.model");
    }


    public static String getCpuAbi() {
        return PropertiesGet.getString("ro.product.cpu.abi");
    }


    public static String getDevice() {
        return PropertiesGet.getString("ro.product.device");
    }


    /**
     * The name of the underlying board, like "goldfish".
     */
    public static String getBoard() {
        return PropertiesGet.getString("ro.product.board");
    }


    /**
     * The name of the hardware (from the kernel command line or /proc).
     */
    public static String getHardware() {
        return PropertiesGet.getString("ro.hardware");
    }

    public static String getBootloader() {
        return PropertiesGet.getString("ro.bootloader");
    }

    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        return telephonyManager.getSubscriberId();
    }


    public static BatteryChangeReceiver sBatteryChangeReceiver;

    public static void registerBatteryChangeListener(Context context) {

        if (sBatteryChangeReceiver == null) {
            sBatteryChangeReceiver = new BatteryChangeReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_BATTERY_CHANGED);
            context.registerReceiver(sBatteryChangeReceiver, filter);
        }
    }

    public static void unRegisterBatteryChangeListener(Context context) {
        if (sBatteryChangeReceiver == null) {
            context.unregisterReceiver(sBatteryChangeReceiver);
            sBatteryChangeReceiver = null;
        }
    }

    public static boolean isCharging() {

        return !(sBatteryChangeReceiver == null || sBatteryChangeReceiver.isCharging());
    }

    public static int getCurrentBatteryLevel() {

        return sBatteryChangeReceiver != null ? sBatteryChangeReceiver.getCurrentLevel() : -1;
    }

    public static void getMac(IpScanner.OnScanListener listener) {
        IpScanner ipScanner = new IpScanner();
        ipScanner.startScan(listener);
    }
}
