package com.byt_eye.tcadmin.modals;

class MainApp2 {
   String website_name;
   String category_id;


    public MainApp2(String website_name, String category_id) {
        this.website_name = website_name;
        this.category_id = category_id;
    }


    public String getWebsite_name() {
        return website_name;
    }

    public void setWebsite_name(String website_name) {
        this.website_name = website_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
