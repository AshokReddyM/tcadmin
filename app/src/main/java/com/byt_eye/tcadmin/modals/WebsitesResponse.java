package com.byt_eye.tcadmin.modals;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class WebsitesResponse implements Serializable{
    private String website_name;
    private String date_of_update;
    private String web_page_link;
    private String filters;
    private String webId;

    public WebsitesResponse() {
    }

    public WebsitesResponse(String website_name, String date_of_update, String web_page_link, String filters) {
        this.website_name = website_name;
        this.date_of_update = date_of_update;
        this.web_page_link = web_page_link;
        this.filters = filters;
    }

    public String getWebsite_name() {
        return website_name;
    }

    public void setWebsite_name(String website_name) {
        this.website_name = website_name;
    }

    public String getDate_of_update() {
        return date_of_update;
    }

    public void setDate_of_update(String date_of_update) {
        this.date_of_update = date_of_update;
    }

    public String getWeb_page_link() {
        return web_page_link;
    }

    public void setWeb_page_link(String web_page_link) {
        this.web_page_link = web_page_link;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

}
