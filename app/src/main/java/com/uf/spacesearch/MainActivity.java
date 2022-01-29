package com.uf.spacesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uf.spacesearch.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        button = findViewById(R.id.Play);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlayGame();
            }
        }) ;
    }
    private void PlayGame() {
        Intent playGameIntent = new Intent(this, GameActivity.class);
        startActivity(playGameIntent);
    }
}