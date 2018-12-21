package com.byt_eye.tcadmin.websites.activity;

import android.app.Activity;
import android.util.Log;

import com.byt_eye.tcadmin.data.DataManager;
import com.byt_eye.tcadmin.modals.CategoryResponse;
import com.byt_eye.tcadmin.modals.CrawlWebsite;
import com.byt_eye.tcadmin.modals.Website;
import com.byt_eye.tcadmin.modals.WebsitesResponse;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WebsitesActivityPresenter {

    private DataManager mDataManager = new DataManager();

    public void getWebsites(final Activity activity, DatabaseReference database) {
        mDataManager.getWebsites(database)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<WebsitesResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<WebsitesResponse> websites) {
                        ((WebsitesActivityMvp) activity).onGettingWebsiteDetails(websites);
                    }

                    @Override
                    public void onError(Throwable e) {

                        ((WebsitesActivityMvp) activity).onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void getCategoriesOfLanguage(final Activity activity, final String language, DatabaseReference databaseReference) {
        mDataManager.getCategoriesOfLanguage(databaseReference)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CategoryResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CategoryResponse> categoriesList) {
                        ((WebsitesActivityMvp) activity).onGettingLangCategoriesList(language,categoriesList);
                    }


                    @Override
                    public void onError(Throwable e) {

                        ((WebsitesActivityMvp) activity).onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getMainModules(final Activity activity, DatabaseReference databaseReference) {
        mDataManager.getModules(databaseReference)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> modulesList) {
                        Log.d("modules", modulesList.get(0));
                        ((WebsitesActivityMvp) activity).onGettingModulesList(modulesList);
                    }


                    @Override
                    public void onError(Throwable e) {

                        ((WebsitesActivityMvp) activity).onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public void crawlWebsite() {
        mDataManager.crawlWebsite().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CrawlWebsite>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CrawlWebsite> crawlWebsites) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}

