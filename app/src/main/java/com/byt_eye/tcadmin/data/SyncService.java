package com.byt_eye.tcadmin.data;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.byt_eye.tcadmin.modals.WebsitesResponse;
import com.byt_eye.tcadmin.utils.AndroidComponentUtil;
import com.byt_eye.tcadmin.utils.NetworkUtil;
import com.byt_eye.tcadmin.utils.RxUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SyncService extends Service {

    private Disposable mDisposable;
    DataManager mDataManager;
    static List<WebsitesResponse> mWebsites;

    public static Intent getStartIntent(Context context, List<WebsitesResponse> websites) {
        mWebsites = websites;
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDataManager = new DataManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Log.i("Starting sync...", "Starting..");

        if (!NetworkUtil.isNetworkConnected(this)) {
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        RxUtil.dispose(mDisposable);
        mDataManager.crawlWebsite(mWebsites)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean status) {
                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null) mDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
