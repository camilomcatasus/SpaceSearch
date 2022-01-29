package com.uf.spacesearch.ui.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uf.spacesearch.DownloadImageTask;
import com.uf.spacesearch.GameActivity;
import com.uf.spacesearch.R;
import com.uf.spacesearch.databinding.FragmentGameBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RequestQueue queue;
    private PageViewModel pageViewModel;
    private FragmentGameBinding binding;
    String image;
    String name;
    public static PlaceholderFragment newInstance(int index, JSONArray jsonArray) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString("response", jsonArray.toString());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        try {
            Random random = new Random();
            JSONObject jsonObj = new JSONArray(getArguments().getString("response")).getJSONObject(random.nextInt(60));
            image = jsonObj.getString("href");
            name = jsonObj.getJSONArray("data").getJSONObject(0).getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        ImageView imageView = (ImageView) root.findViewById(R.id.imageView);
        queue = Volley.newRequestQueue(root.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, image,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String url = new JSONArray(response).getString(0);
                            url = url.substring(0, 4)  + 's' + url.substring(4);
                            new DownloadImageTask( imageView).execute(url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: reload fragment
            }
        });
        queue.add(stringRequest);
        TextView textView = (TextView) root.findViewById(R.id.wordView);
        textView.setText(name);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}