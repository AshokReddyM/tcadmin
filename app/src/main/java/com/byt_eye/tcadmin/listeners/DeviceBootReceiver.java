package com.byt_eye.tcadmin.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.byt_eye.tcadmin.services.DailyService;


public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            DailyService.initDailyService(context, true);
        }
    }
}