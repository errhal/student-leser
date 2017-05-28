package com.example.android.actionbarcompat.styled;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cezary Borowski on 2017-05-27.
 */

public class SQLiteDb
{
    public SQLiteDb(Context context)
    {

    }

    public void sqliteDbUpdateOnce(Context context)
    {
        SQLiteDbHelper sql = new SQLiteDbHelper(context);
        sql.onCreate(sql.getReadableDatabase());

        String wydzial = "";
        String kierunek = "";
        String semestr = "";
        String przedmiot = "";
        int ects;
        int zaliczone;
        List<String[]> lista = readCsv(context);
        for (int i=0; i<lista.size(); i++)
        {
            if (lista.get(i)[0].length() > 0)
                wydzial = lista.get(i)[0];
            if (lista.get(i)[1].length() > 0)
                kierunek = lista.get(i)[1];
            if (lista.get(i)[2].length() > 0)
                semestr = lista.get(i)[2];
            przedmiot = lista.get(i)[3];
            ects = Integer.parseInt(lista.get(i)[4]);
            zaliczone = Integer.parseInt(lista.get(i)[5]);

            sql.addToDb(wydzial, kierunek, semestr, przedmiot, ects, zaliczone);
        }
    }

    public final List<String[]> readCsv(Context context) {
        List<String[]> lista = new ArrayList<>();
        AssetManager assetManager = context.getAssets();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(assetManager.open("dane_uczelni.csv")));
            //BufferedReader in2 = new BufferedReader(new InputStreamReader(R.class.getResourceAsStream("dane_uczelni.csv")));

            String line;
            while ((line = in.readLine()) != null) {
                String[] rowData = line.split(";");
                String date = rowData[0];
                String value = rowData[1];
                lista.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
