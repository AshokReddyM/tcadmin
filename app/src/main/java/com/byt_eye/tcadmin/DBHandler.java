package com.byt_eye.tcadmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 6;
    // Database Name
    private static final String DATABASE_NAME = "tcNewsDbENG";
    // Contacts table name
    private static final String TABLE_NAME = "tc_news_table_english";
    // Shops Table Columns names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String DESCRIPTION = "description";
    private static final String WEBSITE_NAME = "website_name";
    private static final String PUBLISHED_TIME = "published_time";
    private static final String READ_STATUS = "read_status";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "create table tc_news_table_english(id integer primary key, title text,link text,website_name text, published_time text,description text,read_status text)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addNews(String title, String link, String websiteName, String publishedTime, String description, String read_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(LINK, link);
        values.put(WEBSITE_NAME, websiteName);
        values.put(PUBLISHED_TIME, publishedTime);
        values.put(DESCRIPTION, description);
        values.put(READ_STATUS, read_status);
        boolean status = db.insert(TABLE_NAME, null, values) > 0;
        db.close();
        return status;
    }


    public RssFeedModel findLink(String link) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + LINK + " =  \"" + link + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        RssFeedModel dbDataModal = new RssFeedModel();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            dbDataModal.setID(cursor.getString(0));
            dbDataModal.setTitle(cursor.getString(1));
            dbDataModal.setLink(cursor.getString(2));
            cursor.close();
        } else {
            dbDataModal = null;
        }
        db.close();
        return dbDataModal;
    }


    // Getting All Contacts
    public ArrayList<RssFeedModel> getDataList() {
        ArrayList<RssFeedModel> dbDataModals = new ArrayList<>();
        String selectQuery = "SELECT * FROM tc_news_table_english ORDER BY id DESC;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                RssFeedModel dbDataModal = new RssFeedModel();
                dbDataModal.setID(cursor.getString(0));
                dbDataModal.setTitle(cursor.getString(1));
                dbDataModal.setLink(cursor.getString(2));
                dbDataModal.setWebsiteName(cursor.getString(3));
                dbDataModal.setPublishedTime(cursor.getString(4));
                dbDataModal.setDescription(cursor.getString(5));
                dbDataModal.setReadStatus(cursor.getString(6));
                dbDataModals.add(dbDataModal);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return dbDataModals;
    }


    public RssFeedModel getTopRow() {
        RssFeedModel dbDataModal = new RssFeedModel();
        String selectQuery = "SELECT * FROM tc_news_table_english ORDER BY id DESC;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            dbDataModal = new RssFeedModel();
            dbDataModal.setID(cursor.getString(0));
            dbDataModal.setTitle(cursor.getString(1));
            dbDataModal.setLink(cursor.getString(2));
            dbDataModal.setWebsiteName(cursor.getString(3));
            dbDataModal.setPublishedTime(cursor.getString(4));
            dbDataModal.setDescription(cursor.getString(5));
            dbDataModal.setReadStatus(cursor.getString(6));
        }
        cursor.close();
        return dbDataModal;
    }


    public void deleteFirstRow() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            String rowId = cursor.getString(cursor.getColumnIndex(ID));

            db.delete(TABLE_NAME, ID + "=?", new String[]{rowId});
        }
        cursor.close();
        db.close();
    }


    public void updateRow(String rowId, String updateText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(READ_STATUS, updateText);
        db.update(TABLE_NAME, values, "id=" + rowId, null);
        db.close();

    }
}

