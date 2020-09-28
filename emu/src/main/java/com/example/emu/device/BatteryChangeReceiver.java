package com.example.emu.device;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryChangeReceiver extends BroadcastReceiver {

    private boolean mIsCharging;
    private int mCurrentLevel;

    @Override
    public void onReceive(Context context, Intent intent) {


        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
        mCurrentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        switch (status) {

            case BatteryManager.BATTERY_STATUS_CHARGING:
            case BatteryManager.BATTERY_STATUS_FULL:
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                mIsCharging = true;
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                mIsCharging = false;
                break;
        }

    }

    public boolean isCharging() {
        return mIsCharging;
    }

    public int getCurrentLevel() {
        return mCurrentLevel;
    }
}
