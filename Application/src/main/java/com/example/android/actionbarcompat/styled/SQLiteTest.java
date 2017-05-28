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
        int zaliczone = 0;

        sql.addToDb(wydzial, kierunek, semestr, przedmiot, ects, zaliczone);

        wydzial = context.getString(R.string.w1);
        kierunek = context.getString(R.string.k12);
        semestr = context.getString(R.string.s111);
        przedmiot = context.getString(R.string.p1111);
        ects = 5;
        zaliczone = 1;

        sql.addToDb(wydzial, kierunek, semestr, przedmiot, ects, zaliczone);

        wydzial = context.getString(R.string.w1);
        kierunek = context.getString(R.string.k13);
        semestr = context.getString(R.string.s111);
        przedmiot = context.getString(R.string.p1111);
        ects = 3;
        zaliczone = 0;

        sql.addToDb(wydzial, kierunek, semestr, przedmiot, ects, zaliczone);

    }


}
