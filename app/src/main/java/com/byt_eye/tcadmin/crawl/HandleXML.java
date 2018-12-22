package com.byt_eye.tcadmin.crawl;

import android.util.Xml;

import com.byt_eye.tcadmin.modals.RssFeedModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Ashok Reddy M on 7/1/2017.
 */
public class HandleXML {

    private String mFeedTitle, mFeedLink, mFeedDescription, mWebSiteName, mPublishedTime, mFeedImageUrl;
    private int ENCODED_TAG, MAX_TAG;


    public HandleXML(String mFeedTitle, String mFeedLink, String mFeedDescription, String mFeedUrl, String mPublishedTime, String mReadStatus, String mFeedImageUrl, int ENCODED_TAG) {
        this.mFeedTitle = mFeedTitle;
        this.mFeedLink = mFeedLink;
        this.mFeedDescription = mFeedDescription;
        this.mPublishedTime = mPublishedTime;
        this.mFeedImageUrl = mFeedImageUrl;
        this.ENCODED_TAG = ENCODED_TAG;

    }


    public ArrayList<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {

        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;
        String thumbImage = null;
        String imageUrl = null;
        String weblink = null;
        String readStatus = "0";
        boolean isItem = false;
        ArrayList<RssFeedModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();


                if (MAX_TAG > 3) {
                    break;
                }

                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }


                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                switch (name) {
                    case "title":
                        link = null;
                        description = null;
                        title = result;
                        break;

                    case "link":
                        if (mWebSiteName == null) {
                            mFeedLink = link;
                        }
                        link = result;
                        break;

                    case "weblink":
                        if (mWebSiteName == null) {
                            mFeedLink = weblink;
                        }
                        link = result;
                        break;

                    case "description":
                        description = result;

                        try {
                            Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                            Matcher m = p.matcher(result);
                            if (m.find()) {
                                imageUrl = m.group(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;


                    case "pubDate":
                        pubDate = settingCurrentTime();
                        break;

                    case "media:thumbnail":
                        imageUrl = xmlPullParser.getAttributeValue(null, "url");
                        break;
                    case "enclosure":
                        imageUrl = xmlPullParser.getAttributeValue(null, "url");
                        break;

                    case "thumbImage":
                        imageUrl = thumbImage;
                        break;

                    case "url":
                        imageUrl = thumbImage;
                        break;

                    case "content:encoded":
                        Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                        Matcher m = p.matcher(result);
                        if (m.find()) {
                            imageUrl = m.group(1);
                        }

                        break;
                }


                if (ENCODED_TAG == 1) {

                    if (title != null && link != null && description != null && pubDate != null && imageUrl != null) {
                        if (isItem) {
                            RssFeedModel item = new RssFeedModel();
                            item.setTitle(title);
                            item.setLink(link);
                            item.setDescription(description);
                            item.setPublishedTime(pubDate);
                            item.setWebsiteName(mWebSiteName);
                            item.setReadStatus(readStatus);
                            item.setImageUrl(imageUrl);
                            items.add(item);
                            MAX_TAG++;
                        } else {
                            mFeedTitle = title;
                            mFeedLink = link;
                            mFeedDescription = description;
                            mPublishedTime = pubDate;
                            mFeedImageUrl = imageUrl;
                        }

                        title = null;
                        link = null;
                        description = null;
                        imageUrl = null;
                        pubDate = null;
                        isItem = false;
                    }
                } else if (ENCODED_TAG == 0) {
                    if (title != null && link != null && description != null && pubDate != null) {
                        if (isItem) {
                            RssFeedModel item = new RssFeedModel();
                            item.setTitle(title);
                            item.setLink(link);
                            item.setDescription(description);
                            item.setPublishedTime(pubDate);
                            item.setWebsiteName(mWebSiteName);
                            item.setReadStatus(readStatus);
                            item.setImageUrl(imageUrl);
                            items.add(item);
                            MAX_TAG++;
                        } else {
                            mFeedTitle = title;
                            mFeedLink = link;
                            mFeedDescription = description;
                            mPublishedTime = pubDate;
                            mFeedImageUrl = imageUrl;

                        }

                        title = null;
                        link = null;
                        description = null;
                        imageUrl = null;
                        pubDate = null;
                        isItem = false;
                    }

                }
            }

        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }

        return items;
    }

    public String settingCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }

}