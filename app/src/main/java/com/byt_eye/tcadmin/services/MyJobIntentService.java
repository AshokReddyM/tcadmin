package com.byt_eye.tcadmin.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;

import com.byt_eye.tcadmin.data.DataManager;
import com.byt_eye.tcadmin.modals.WebsitesResponse;

import java.util.List;
import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyJobIntentService extends JobIntentService {

    List<WebsitesResponse> websitesList;
    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;
    final String TAG = "MyJobIntenetService";

    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent intent) {
        // We have received work to do.  The system or framework is already
        // holding a wake lock for us at this point, so we can just go.

        websitesList = (List<WebsitesResponse>) intent.getSerializableExtra("websites");

        new DataManager().crawlWebsite(websitesList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean status) {
                        System.out.println(status);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.wtf(TAG, "All work complete");
        toast("All work complete");
    }

    final Handler mHandler = new Handler();

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyJobIntentService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

