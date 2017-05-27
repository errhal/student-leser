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

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.entities.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
//        setContentView(button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu from menu resource (res/menu/main)
        getMenuInflater().inflate(R.menu.main, menu);

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
        List<Button> buttons = Collections.synchronizedList(new ArrayList<Button>());
        for (int i = 0; i < 4; i++) {
            buttons.add(new Button(this));
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
        setContentView(new View(this));
        for (int i = 0; i < 10; i++) {
            Button button = new Button(this);
            if (i % 2 == 1) {
                button.setX(540);
                button.setY((i / 2) * 200);
            } else {
                button.setY((i / 2) * 200);
            }
            button.setText("Option " + i);
            addContentView(button, new LinearLayout.LayoutParams(540, 200));
        }
    }

    private void showHistory() {
        setContentView(new View(this));
        Map<String, List<com.example.android.entities.Activity>> activities = new LinkedHashMap<>();
        Activity a1 = new Activity();
        a1.setId(1);
        a1.setName("Informatyka");
        a1.setPassed(true);

        activities.put("Semestr 1", Arrays.asList(a1));
        activities.put("Semestr 2", new ArrayList<com.example.android.entities.Activity>());
        activities.put("Semestr 3", new ArrayList<com.example.android.entities.Activity>());

        int index = 0;
        for(String s : activities.keySet()) {
            if(activities.get(s).isEmpty()) continue;
            TextView textView = new TextView(this);
            textView.setText(s);
            textView.setTextSize(24);
            textView.setX(400);
            textView.setY((++index)*120);
            addContentView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120));
            for(Activity a : activities.get(s)) {
                Button button = new Button(this);
                button.setText(a.getName());
                button.setY((++index) * 120);
                addContentView(button, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
            }
        }
    }
}
