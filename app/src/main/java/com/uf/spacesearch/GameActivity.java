package com.uf.spacesearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.json.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uf.spacesearch.ui.main.PlaceholderFragment;
import com.uf.spacesearch.ui.main.SectionsPagerAdapter;
import com.uf.spacesearch.databinding.ActivityGameBinding;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    RequestQueue queue;
    String url = "https://images-api.nasa.gov/search?q=space%station&media_type=image";
    JSONArray images;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        try {
            images = new JSONObject(extras.getString("json")).getJSONObject("collection").getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PlaceholderFragment f = PlaceholderFragment.newInstance(0, images);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragmentContainerView,f, "tag");
        ft.commit();
        //SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), images);

        //ViewPager viewPager = binding.viewPager;

        UpdateScore();

        //viewPager.setAdapter(sectionsPagerAdapter);
        //TabLayout tabs = binding.tabs;
        //tabs.setupWithViewPager(viewPager);
        getSupportFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                //TODO: upon listening to fragment result, update score
                ++score;
                UpdateScore();
                /*sectionsPagerAdapter.destroyItem(viewPager,(int)result.get("position"), sectionsPagerAdapter.getItem((int)result.get("position")));
                sectionsPagerAdapter.fragmentHashMap.remove((int)result.getInt("position"));
                Fragment f = sectionsPagerAdapter.getItem((int)result.get("position"));

                //f.onCreate();
                //viewPager.addView(f.getView());
                sectionsPagerAdapter.notifyDataSetChanged();
                tabs.setupWithViewPager(viewPager);*/
                PlaceholderFragment f = PlaceholderFragment.newInstance(0, images);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainerView,f, "tag");
                ft.commit();
            }
        });
        Button button = (Button) findViewById(R.id.home);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainMenu();
            }
        }) ;
    }

    private void MainMenu() {
        //do some stuff
        startActivity(new Intent(this, MainActivity.class));
    }

    public void UpdateScore() {
        TextView scoreText = (TextView) findViewById(R.id.scorenum);
        scoreText.setText(String.valueOf(score));
    }
}