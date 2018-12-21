package com.byt_eye.tcadmin.websites.activity;

import com.byt_eye.tcadmin.modals.CategoryResponse;
import com.byt_eye.tcadmin.modals.Website;
import com.byt_eye.tcadmin.modals.WebsitesResponse;

import java.util.List;

public interface WebsitesActivityMvp {
    void onGettingDetails(List<WebsitesResponse> websites);
    void onError(String message);
    void onGettingModulesList(List<String> modulesList);
    void onGettingLangCategoriesList(String language, List<CategoryResponse> categoriesList);
    void onGettingWebsiteDetails(List<WebsitesResponse> websites);
}
