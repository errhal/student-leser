package com.example.android.actionbarcompat.styled;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Czarek on 2017-05-28.
 */

public class CombinationGenerator
{
    public List kombinacje(List<SQLiteDbHelper.Wyniki> lista, int minEcts)
    {
        int prawdziwyMinEcts = minEcts;
        int liczbaEl = 0;
        //modyfikuje List tak, by Wyniki z zaliczone=0 byly na poczatku
        for (int i=0; i < lista.size(); i++)
        {

            if (lista.get(i).zaliczone != 0) {
                prawdziwyMinEcts -= lista.get(i).ects;
                Collections.sort(lista, new Comparator<SQLiteDbHelper.Wyniki>(){
                    public int compare(SQLiteDbHelper.Wyniki a, SQLiteDbHelper.Wyniki b)
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
        List<String> wynik = new ArrayList<>();
        //generowanie liczby w postaci binarnej=kombinacje 0 1 oznaczajace przynaleznosc elementu listy do zbioru
        for (int i=0; i < wielkoscLiczby; i++)
        {
            String a = Integer.toBinaryString(i);
            int b = a.length();
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
}
