package com.byt_eye.tcadmin.modals;

import java.io.Serializable;

public class Websites implements Serializable {
    private String website_name;
    private String web_page_link;
    private String date_of_update;
    private String filters;
    private String category_id;
    private String language;

    public Websites(String website_name, String web_page_link, String date_of_update, String filters, String category_id, String language) {
        this.website_name = website_name;
        this.web_page_link = web_page_link;
        this.date_of_update = date_of_update;
        this.filters = filters;
        this.category_id = category_id;
        this.language = language;
    }


    public String getWebsite_name() {
        return website_name;
    }

    public void setWebsite_name(String website_name) {
        this.website_name = website_name;
    }

    public String getWeb_page_link() {
        return web_page_link;
    }

    public void setWeb_page_link(String web_page_link) {
        this.web_page_link = web_page_link;
    }

    public String getDate_of_update() {
        return date_of_update;
    }

    public void setDate_of_update(String date_of_update) {
        this.date_of_update = date_of_update;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
