package com.byt_eye.tcadmin.modals;

import java.io.Serializable;

public class Websites implements Serializable {
    String Website_name;
    String web_page_link;
    String date_of_update;
    String filters;

    public Websites(String website_name, String web_page_link, String date_of_update, String filters) {
        Website_name = website_name;
        this.web_page_link = web_page_link;
        this.date_of_update = date_of_update;
        this.filters = filters;
    }

    public String getWebsite_name() {
        return Website_name;
    }

    public void setWebsite_name(String website_name) {
        Website_name = website_name;
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
}
