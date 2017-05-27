package com.example.android.actionbarcompat.styled;

import android.content.Context;

/**
 * Created by Cezary Borowski on 2017-05-27.
 */

public class SQLiteTest
{
    public SQLiteTest(Context context) {
        SQLiteDbHelper sql = new SQLiteDbHelper(context);

        String wydzial = context.getString(R.string.w1);
        String kierunek = context.getString(R.string.k11);
        String semestr = context.getString(R.string.s111);
        String przedmiot = context.getString(R.string.p1111);
        int ects = 4;
        sql.addToDb(wydzial, kierunek, semestr, przedmiot, ects);
    }


}
