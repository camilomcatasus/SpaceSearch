package com.uf.spacesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button button;
    boolean searching = false;
    TextView textView ;
    RequestQueue queue;
    String url = "https://images-api.nasa.gov/search?q=space%20station&media_type=image";
    JSONArray images;
    Context context = this;
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
        Button galleryButton = (Button) findViewById(R.id.gallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EnterGallery();
            }
        });
        Button creditsButton = (Button) findViewById(R.id.credits);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EnterCredits();
            }
        });
    }

    private void PlayGame() {
        if(!searching) {
            searching = true;
            queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent playGameIntent = new Intent(context, GameActivity.class);
                            playGameIntent.putExtra("json", response);
                            startActivity(playGameIntent);
                            searching = false;
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO: display an error to user
                    searching = false;
                }
            });
            queue.add(stringRequest);
        }
    }

    private void EnterGallery() {
        startActivity(new Intent(this, GalleryActivity.class));
    }

    private void EnterCredits() {
        startActivity(new Intent(this, CreditsActivity.class));
    }

}