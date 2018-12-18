package com.byt_eye.tcadmin.listeners;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.byt_eye.tcadmin.listeners.RssPullService;
import com.byt_eye.tcadmin.utils.NotificationUtil;

public class AlarmBroadcaster extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        ComponentName comp = new ComponentName(context.getPackageName(), RssPullService.class.getName());
        RssPullService.enqueueWork(context, (intent.setComponent(comp)));
        Log.d("AlarmBroadcaster", "Alarm just fired");
        /* showing dummy notification */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NotificationUtil.showCustomBigPictureNotification(context, "Schedule Background task status", "Schedule task fired",
                        "https://3.bp.blogspot.com/-A5feB9RwsKs/VxhU99nEgoI/AAAAAAAAPBg/KzYoCu27rj8T2Lx0FHcRiByQe2amQ40AQCLcB/s1600/freecharge-add-rs250-get-rs25-cashback-LOAD250.png");
            }
        }, 1000);

    }
}