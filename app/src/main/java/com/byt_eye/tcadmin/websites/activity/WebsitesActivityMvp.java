package com.byt_eye.tcadmin.websites.activity;

import com.byt_eye.tcadmin.modals.Website;

import java.util.List;

public interface WebsitesActivityMvp {
    void onGettingDetails(List<Website> websites);
    void onError(String message);
    void onGettingModulesList(List<String> modulesList);
}
