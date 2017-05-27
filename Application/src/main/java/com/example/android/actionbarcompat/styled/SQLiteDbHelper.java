package com.example.android.actionbarcompat.styled;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Czarek on 2017-05-27.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper
{
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "baza.db";

    public static class Entry implements BaseColumns {
        public static final String TABLENAME = "Tablica";
        public static final String WYDZIAL = "wydz";
        public static final String KIERUNEK = "kier";
        public static final String SEMESTR = "semestr";
        public static final String PRZEDMIOT = "przedmiot";
        public static final String ECTS = "10";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLENAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.KIERUNEK + " TEXT," +
                    Entry.PRZEDMIOT + " TEXT," +
                    Entry.ECTS + " INTEGER," +
                    Entry.SEMESTR + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLENAME;


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
