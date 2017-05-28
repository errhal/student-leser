package com.example.android.actionbarcompat.styled;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Czarek on 2017-05-27.
 */

public class SQLiteDbHelper extends SQLiteOpenHelper
{
    public SQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "baza.db";

    public static class Entry implements BaseColumns {
        public static final String TABLENAME = "Tablica";
        public static final String WYDZIAL = "wydzial";
        public static final String KIERUNEK = "kierunek";
        public static final String SEMESTR = "semestr";
        public static final String PRZEDMIOT = "przedmiot";
        public static final String ECTS = "ects";
        public static final String ZALICZONE = "zaliczone";
    }

    public class Wyniki
    {
        public String przedmiot;
        public int ects;
        public int zaliczone;
        Wyniki(String przedmiot, int ects, int zaliczone)
        {
            this.przedmiot = przedmiot;
            this.ects = ects;
            this.zaliczone = zaliczone;
        };
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLENAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.WYDZIAL + " TEXT," +
                    Entry.KIERUNEK + " TEXT," +
                    Entry.SEMESTR + " TEXT," +
                    Entry.PRZEDMIOT + " TEXT," +
                    Entry.ECTS + " INTEGER," +
                    Entry.ZALICZONE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLENAME;


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
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

    /*TextView mojna= (TextView) findViewById(R.id.mojnapis);
    String input = mojna.getText().toString();*/

    public void addToDb(String wydzial, String kierunek, String semestr, String przedmiot, int ects, int zaliczone)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the key
        ContentValues values = new ContentValues();

        values.put(Entry.WYDZIAL, wydzial);
        values.put(Entry.KIERUNEK, kierunek);
        values.put(Entry.SEMESTR, semestr);
        values.put(Entry.PRZEDMIOT, przedmiot);
        values.put(Entry.ECTS, ects);
        values.put(Entry.ZALICZONE, zaliczone);


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Entry.TABLENAME, null, values);
    }

    public List<Wyniki> queryDb(String wydzial, String kierunek, String semestr)
    {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                //Entry._ID,
                //Entry.WYDZIAL,
                //Entry.KIERUNEK,
                //Entry.SEMESTR,
                Entry.PRZEDMIOT,
                Entry.ECTS,
                Entry.ZALICZONE,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Entry.WYDZIAL + " = ? AND " + Entry.KIERUNEK + " = ? AND " + Entry.SEMESTR + " = ?";
        String[] selectionArgs = { wydzial, kierunek, semestr };

        // How you want the results sorted in the resulting Cursor
        //String sortOrder =
        //        Entry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                Entry.TABLENAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null//sortOrder                                 // The sort order
        );

        List<Wyniki> wyniki = new ArrayList<>();

        while(cursor.moveToNext()) {
            String przedmiot = cursor.getString(
                    cursor.getColumnIndexOrThrow(Entry.PRZEDMIOT));

            int ects = cursor.getInt(
                    cursor.getColumnIndexOrThrow(Entry.ECTS));

            int zaliczone = cursor.getInt(
                    cursor.getColumnIndexOrThrow(Entry.ZALICZONE));

            wyniki.add(new Wyniki(przedmiot, ects, zaliczone));
        }

        cursor.close();
        return wyniki;
    }
    /*
    public List kombinacje(List<Wyniki> lista, int minEcts)
    {
        int prawdziwyMinEcts = minEcts;
        int liczbaEl = 0;
        //modyfikuje List tak, by Wyniki z zaliczone=0 byly na poczatku
        for (int i=0; i < lista.size(); i++)
        {

            if (lista.get(i).zaliczone != 0) {
                prawdziwyMinEcts -= lista.get(i).ects;
                Collections.sort(lista, new Comparator<Wyniki>(){
                    public int compare(Wyniki a, Wyniki b)
                    {
                        if (a.zaliczone >= b.zaliczone)
                            return 1;
                        else
                            return 0;       //0 na poczatku ??
                    }
                });
            }
            else
                liczbaEl++;
        }
        double wielkoscLiczby = Math.pow(2, liczbaEl);
        List<String> wynik = new ArrayList<String>();
        //generowanie liczby w postaci binarnej=kombinacje 0 1 oznaczajace przynaleznosc elementu listy do zbioru
        for (int i=0; i < wielkoscLiczby; i++)
        {
            String a = Integer.toBinaryString(i);
            int b = Integer.toBinaryString(i).length();
            int pomocniczailoscects=prawdziwyMinEcts;
            //sprawdza czy dany ciag generuje wystarczajaca ilosc ects
            for(int j=0;j<b;++j) {
                if((int)a.charAt(j) == '1')
                pomocniczailoscects-=lista.get(i).ects;
            }
            if(pomocniczailoscects<=0) {
                int c = liczbaEl - b;   //wymagana ilosc dopisanych zer
                for (int j = 0; j < c; j++) {
                    a = "0" + a;
                }

                String d = "";
                for (int j = 0; j < liczbaEl; j++) {
                    if ((int) a.charAt(j) == '1') {
                        d += lista.get(j).przedmiot + " ";
                    }
                }
                wynik.add(d);
            }
            //ciag nie spelnial wymogow wystarczajacej ilosci ects
            else{continue;}
        }
        return wynik;
    }
*/

}
