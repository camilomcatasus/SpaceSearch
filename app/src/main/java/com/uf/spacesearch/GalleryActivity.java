package com.uf.spacesearch;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageView;

import com.uf.spacesearch.databinding.GalleryActivityBinding;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        Button button = (Button) findViewById(R.id.home);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainMenu();
            }});
        ImageView load = (ImageView) findViewById(R.id.imageView); //we get a null pointer exception here, gotta find outn why
        load.setImageResource(R.drawable.loading);
        AnimationDrawable loading = (AnimationDrawable) load.getDrawable();
        loading.start();
    }

    private void MainMenu() {
        startActivity(new Intent(this, MainActivity.class));
    }
}