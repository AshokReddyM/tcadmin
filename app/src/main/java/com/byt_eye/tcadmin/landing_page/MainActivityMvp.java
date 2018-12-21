package com.byt_eye.tcadmin.landing_page;

import com.byt_eye.tcadmin.modals.CategoryResponse;
import com.byt_eye.tcadmin.modals.WebsitesResponse;

import java.util.List;

public interface MainActivityMvp {
    void onGettingLangCategoriesList(String language, List<CategoryResponse> categoriesList);

    void onError(String message);

    void onGettingWebsiteDetails(List<WebsitesResponse> websites);
}
