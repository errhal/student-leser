package com.example.android.actionbarcompat.styled;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GalleryActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showDirectories();
    }

    public void showDirectories() {
//        setContentView(R.layout.directory);
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.directory);
        ImageButton image1 = prepareImageButton();
        float x = 170;
        float y = 30;
        image1.setX(x);
        image1.setY(y);
        setContentView(image1, new LinearLayout.LayoutParams(300, 300));
//        linearLayout.addView(image1, 700, 700);
//        linearLayout.addView(image2, 700, 700);
//        linearLayout.addView(image3, 700, 700);
//        linearLayout.addView(image4, 700, 700);

        for (int i = 1; i < 9; i++) {
            ImageButton image = prepareImageButton();
            image.setY(y);
            if (i % 2 == 0) {
                image.setX(170);
            } else {
                y+= 300;
                image.setX(650);
            }
            addContentView(image, new LinearLayout.LayoutParams(300, 300));
        }
    }

    public ImageButton prepareImageButton() {
        ImageButton imageButton = new ImageButton(this);
        imageButton.setBackgroundResource(R.drawable.directory);
        imageButton.setLeft(10);
        imageButton.setRight(10);
        return imageButton;
    }


}
