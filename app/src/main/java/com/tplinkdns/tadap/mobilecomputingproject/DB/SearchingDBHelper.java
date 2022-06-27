package com.tplinkdns.tadap.mobilecomputingproject.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SearchingDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Searching.db";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS "+ FavoriteTableInfo.SEARCHING_TABLE_NAME +" ("+
                    FavoriteTableInfo.CONTENT_ID+" INTEGER PRIMARY KEY,"+
                    FavoriteTableInfo.CAMP_NAME+" TEXT,"+
                    FavoriteTableInfo.CAMP_PUBLIC_NAME+" TEXT,"+
                    FavoriteTableInfo.IMAGE_URL+" TEXT,"+
                    FavoriteTableInfo.URL+" TEXT,"+
                    FavoriteTableInfo.CAMP_MAP_X+" TEXT,"+
                    FavoriteTableInfo.CAMP_MAP_Y+" TEXT,"+
                    FavoriteTableInfo.CAMP_ADDRESS+" TEXT,"+
                    FavoriteTableInfo.CAMP_PHONE_NUM+" TEXT)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + FavoriteTableInfo.SEARCHING_TABLE_NAME;

    public SearchingDBHelper(@Nullable Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
