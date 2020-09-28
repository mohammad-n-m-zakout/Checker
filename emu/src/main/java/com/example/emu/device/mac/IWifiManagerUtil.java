package com.example.emu.device.mac;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class IWifiManagerUtil {

    private static String INVALID_ADDRESS = "02:00:00:00:00:00";

    public static String getMacAddress(Context context) {
        String address;
        if (!TextUtils.isEmpty(address = getMacAddressLevel1(context))
                && !INVALID_ADDRESS.endsWith(address)) {
            return address;
        }

        if (!TextUtils.isEmpty(address = getMacAddressLevel0(context))
                && !INVALID_ADDRESS.endsWith(address)) {
            return address;
        }

        return address;
    }

    private static String getMacAddressLevel0(Context context) {
        String macAddress = null;
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());
            if (null != info) {
                macAddress = info.getMacAddress();
            }
            if (!TextUtils.isEmpty(macAddress)) {
                return macAddress;
            }
            if (wifiManager != null && !wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
                wifiManager.setWifiEnabled(false);
            }
            info = (null == wifiManager ? null : wifiManager.getConnectionInfo());
            if (null != info) {
                macAddress = info.getMacAddress();
            }
        } catch (Exception e) {
        }

        return macAddress;
    }

    private static String getMacAddressLevel1(Context context) {
        String macAddress = null;
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            Field IWifiManagerService = wifiManager.getClass().getDeclaredField("mService");
            IWifiManagerService.setAccessible(true);
            Object service = IWifiManagerService.get(wifiManager);
            Method getConnectionInfo = service.getClass().getDeclaredMethod("getConnectionInfo");
            getConnectionInfo.setAccessible(true);
            WifiInfo info = (WifiInfo) getConnectionInfo.invoke(service);
            if (info != null) {

            } else if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
                wifiManager.setWifiEnabled(false);
                info = (WifiInfo) getConnectionInfo.invoke(service);
            }

            try {
                Field mMacAddress = info.getClass().getDeclaredField("mMacAddress");
                mMacAddress.setAccessible(true);
                macAddress = (String) mMacAddress.get(info);
                if (!TextUtils.isEmpty(macAddress)) {
                    return macAddress;
                }
            } catch (Exception e) {

            }

            if (info != null) {
                macAddress = info.getMacAddress();
            }
        } catch (Exception e) {
        }
        return macAddress;
    }

}
