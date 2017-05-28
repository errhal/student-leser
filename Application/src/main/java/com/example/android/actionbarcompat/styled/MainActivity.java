/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.actionbarcompat.styled;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.MenuView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.entities.Activity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This sample shows you how to use ActionBarCompat with a customized theme. It utilizes a split
 * action bar when running on a device with a narrow display, and show three tabs.
 * <p>
 * This Activity extends from {@link ActionBarActivity}, which provides all of the function
 * necessary to display a compatible Action Bar on devices running Android v2.1+.
 * <p>
 * The interesting bits of this sample start in the theme files
 * ('res/values/styles.xml' and 'res/values-v14</styles.xml').
 * <p>
 * Many of the drawables used in this sample were generated with the
 * 'Android Action Bar Style Generator': http://jgilfelt.github.io/android-actionbarstylegenerator
 */
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.sample_main);
        // Set the Action Bar to use tabs for navigation
        ActionBar ab = getSupportActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Add three tabs to the Action Bar for display
        ab.addTab(ab.newTab().setText("Przedmioty").setTabListener(this));
        ab.addTab(ab.newTab().setText("Zaliczenia").setTabListener(this));
        ab.addTab(ab.newTab().setText("Historia").setTabListener(this));
        showButtons();

        //this.addContentView();
//        setContentView(button);

        SQLiteDb sql = new SQLiteDb(this);
        SQLiteDbHelper sqlhlp = new SQLiteDbHelper(this);
        sql.sqliteDbUpdateOnce(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu from menu resource (res/menu/main)
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showGallery(item);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a tab is selected.
        if (tab.getPosition() == 0) {
            showButtons();
        } else if (tab.getPosition() == 1) {
            showOptions();
        } else {
            showHistory();
        }
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a previously selected tab is unselected.
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a previously selected tab is selected again.
    }

    private void showButtons() {
        final List<Button> buttons = Collections.synchronizedList(new ArrayList<Button>());
        for (int i = 0; i < 4; i++) {
            Button button = new Button(this);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showActivityDetails(view);
                }
            });
            buttons.add(button);
        }

        buttons.get(0).setText("Programowanie");
        buttons.get(0).setY(150);
        buttons.get(1).setText("Sieci komputerowe");
        buttons.get(1).setY(300);
        buttons.get(2).setText("Wychowanie fizyczne");
        buttons.get(2).setY(450);
        buttons.get(3).setText("Analiza danych");
        buttons.get(3).setY(600);
        setContentView(buttons.get(0), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        addContentView(buttons.get(1), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        addContentView(buttons.get(2), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        addContentView(buttons.get(3), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));




    }


    private void showOptions() {
        View view = new View(this);
        setContentView(view);
        for (int i = 0; i < 10; i++) {
            Button button = new Button(this);
            if (i % 2 == 1) {
                button.setX(540);
                button.setY((i / 2) * 200);
            } else {
                button.setY((i / 2) * 200);
            }
            button.setText("Opcja nr " + (i+1));
            addContentView(button, new LinearLayout.LayoutParams(540, 200));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //SQLiteDb sql = new SQLiteDb(view.getContext());
                    SQLiteDbHelper sqlhlp = new SQLiteDbHelper(view.getContext());

                    //Lub
                    // List<SQLiteDbHelper.Wyniki> lista = sqlhlp.queryDb(
                    //        view.getContext().getString(R.string.w1),
                    //        view.getContext().getString(R.string.k12),
                    //        view.getContext().getString(R.string.s111));
                    List<SQLiteDbHelper.Wyniki> lista = sqlhlp.queryDb(
                            "Wydział Matematyki i Nauk Informacyjnych",
                            "Informatyka",
                            "Semestr 1");

                    AlertDialog aa = new AlertDialog.Builder(view.getContext()).create();

                    String ssd, wynik_ostateczny = "";
                    for (int i = 0; i < lista.size(); i++) {
                        ssd = lista.get(i).przedmiot + " " + lista.get(i).ects + "\n";
                        wynik_ostateczny += ssd;
                    }
                    aa.setMessage(wynik_ostateczny);
                    aa.setTitle("baza");
                    aa.show();

                }
            });


        }

        Button button = new Button(this);
        button.setX(540);
        button.setY((11 / 2) * 200);
        button.setText("Co zaliczyc?");
        addContentView(button, new LinearLayout.LayoutParams(540, 200));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Obliczanie optymalnych przedmiotów do zaliczenia - sypie się
                SQLiteDbHelper sqlhlp = new SQLiteDbHelper(view.getContext());
                List<SQLiteDbHelper.Wyniki> lista = sqlhlp.queryDb(
                        view.getContext().getString(R.string.w1),
                        view.getContext().getString(R.string.k11),
                        view.getContext().getString(R.string.s111));
                CombinationGenerator kombi = new CombinationGenerator();
                List<String> listaZaliczenia = kombi.kombinacje(lista, 25);
                String napis = kombi.napisKoncowy(listaZaliczenia);

                AlertDialog aa = new AlertDialog.Builder(view.getContext()).create();

                aa.setMessage(napis);
                aa.setTitle("Co zaliczyc?");
                aa.show();

            }
        });





    }

    private void showHistory() {
        setContentView(new View(this));
        Map<String, List<SQLiteDbHelper.Wyniki>> activities = new LinkedHashMap<>();
        activities.put("Semestr 1", (new SQLiteDbHelper(this)).queryDb("Wydział Matematyki i Nauk Informacyjnych",
                "Informatyka",
                "Semestr 1"));

        activities.put("Semestr 2", (new SQLiteDbHelper(this)).queryDb("Wydział Matematyki i Nauk Informacyjnych",
                "Informatyka",
                "Semestr 2"));

        activities.put("Semestr 3", (new SQLiteDbHelper(this)).queryDb("Wydział Matematyki i Nauk Informacyjnych",
                "Informatyka",
                "Semestr 3"));

        int index = 0;
        for (String s : activities.keySet()) {
            if (activities.get(s).isEmpty()) continue;
            TextView textView = new TextView(this);
            textView.setText(s);
            textView.setTextSize(24);
            textView.setX(400);
            textView.setY((++index) * 120);
            addContentView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120));
            for (SQLiteDbHelper.Wyniki a : activities.get(s)) {
                Button button = new Button(this);
                button.setText(a.przedmiot);
                if ((new Random()).nextBoolean()) button.setBackgroundColor(Color.RED);
                else button.setBackgroundColor(Color.GREEN);
                button.setY((++index) * 120);
                addContentView(button, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
            }
        }
    }


    public void showActivityDetails(View view) {

        LinearLayout l1 = new LinearLayout(this);
        LinearLayout l2 = new LinearLayout(this);
        LinearLayout l3 = new LinearLayout(this);
        LinearLayout l4 = new LinearLayout(this);
        l1.setGravity(Gravity.CENTER_HORIZONTAL);
        l3.setY(250);
        l4.setGravity(Gravity.CENTER_HORIZONTAL);
        l4.setY(400);

        TextView t1 = prepareHeader();
        t1.setText(((TextView)view).getText());

        TextView t2 = new TextView(this);
        t2.setText("ECTS: " + 5);
        t2.setTextSize(18);
        t2.setY(150);

        TextView t3 = prepareHeader();
        t3.setText("Notatki:");

        l1.addView(t1);
        l2.addView(t2);

        CheckBox checkBox = new CheckBox(this);
        checkBox.setHeight(120);
        checkBox.setText("Zaliczone");
        l3.addView(checkBox);
        l4.addView(t3);


//        layout.addView(textView);
//        addContentView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
//        addContentView(checkBox, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));

        //        TextView passed = new TextView(this);
//        layout.addView(checkBox);
//        addContentView(checkbox, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        layout.addView(checkbox);
        setContentView(l1);
        addContentView(l2, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addContentView(l3, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addContentView(l4, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void showGallery(MenuItem v) {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    public TextView prepareHeader() {
        TextView textView = new TextView(this);
        textView.setTextSize(24);
        return textView;
    }

    public LinearLayout createLayout() {
        LinearLayout layout = new LinearLayout(null);
        return layout;
    }

    public void onCheckboxClicked(View view) {

    }
}
