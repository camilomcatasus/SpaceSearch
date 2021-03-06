package com.uf.spacesearch;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;

import com.uf.spacesearch.databinding.CreditsActivityBinding;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CreditsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits_activity);

        Button button = (Button) findViewById(R.id.home);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainMenu();
            }
        }) ;
    }

    private void MainMenu() {
        startActivity(new Intent(this, MainActivity.class));
    }
}