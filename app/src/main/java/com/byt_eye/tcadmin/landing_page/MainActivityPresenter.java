package com.byt_eye.tcadmin.landing_page;

import android.app.Activity;

import com.byt_eye.tcadmin.data.DataManager;
import com.byt_eye.tcadmin.data.FirebaseDataManager;
import com.byt_eye.tcadmin.modals.CategoryResponse;
import com.byt_eye.tcadmin.modals.WebsitesResponse;
import com.byt_eye.tcadmin.websites.activity.WebsitesActivityMvp;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter {
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
                        ((MainActivity) activity).onGettingWebsiteDetails(websites);
                    }

                    @Override
                    public void onError(Throwable e) {

                        ((MainActivity) activity).onError(e.getMessage());
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
                        ((MainActivityMvp) activity).onGettingLangCategoriesList(language,categoriesList);
                    }


                    @Override
                    public void onError(Throwable e) {

                        ((MainActivityMvp) activity).onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void crawlWebsite(String web_page_link) {
        mDataManager.crawlWebsite(web_page_link);

    }
}
