package com.byt_eye.tcadmin.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.byt_eye.tcadmin.utils.DailyService;

public class PlugInControlReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == Intent.ACTION_POWER_CONNECTED) {
            DailyService.initDailyService(context, true);
            Toast.makeText(context, "isCharging: " + true, Toast.LENGTH_LONG).show();
        } else if (intent.getAction() == Intent.ACTION_POWER_DISCONNECTED) {
            DailyService.initDailyService(context, true);
            Toast.makeText(context, "isCharging: " + false, Toast.LENGTH_LONG).show();
        }
    }
}
