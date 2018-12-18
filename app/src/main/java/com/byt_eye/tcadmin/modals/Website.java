package com.byt_eye.tcadmin.modals;

import android.support.annotation.Nullable;

public class Website {
    private String websiteName;

    private String ImageKeyword;

    private String TitleKeyword;

    private String ContentKeyword;

    private String UpdateDate;
    private int ImageTag;

    public Website() {
    }

    public Website(String websiteName, String imageKeyword, String titleKeyword, String contentKeyword, String updateDate, int imageTag) {
        this.websiteName = websiteName;
        ImageKeyword = imageKeyword;
        TitleKeyword = titleKeyword;
        ContentKeyword = contentKeyword;
        UpdateDate = updateDate;
        ImageTag = imageTag;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getImageKeyword() {
        return ImageKeyword;
    }

    public void setImageKeyword(String imageKeyword) {
        ImageKeyword = imageKeyword;
    }

    public String getTitleKeyword() {
        return TitleKeyword;
    }

    public void setTitleKeyword(String titleKeyword) {
        TitleKeyword = titleKeyword;
    }

    public String getContentKeyword() {
        return ContentKeyword;
    }

    public void setContentKeyword(String contentKeyword) {
        ContentKeyword = contentKeyword;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public int getImageTag() {
        return ImageTag;
    }

    public void setImageTag(int imageTag) {
        ImageTag = imageTag;
    }
}
