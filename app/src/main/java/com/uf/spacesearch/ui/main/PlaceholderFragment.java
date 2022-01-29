package com.uf.spacesearch.ui.main;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    int index = 0;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        RelativeLayout relativeLayout = (RelativeLayout) root.findViewById(R.id.letterContainer);
        ArrayList<Character> letters = getLetters(name);
        Collections.shuffle(letters);
        View whatToAdd = LayoutInflater.from(root.getContext()).inflate(R.layout.single_row, null, false);
        LinearLayout single = whatToAdd.findViewById(R.id.singleRow);

        if(letters.size() > 6)
            whatToAdd = LayoutInflater.from(relativeLayout.getContext()).inflate(R.layout.double_row, null, false);
        else
        {
            for (Character c: letters)
            {
                Button testButton = new Button(root.getContext());

                testButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                testButton.setText(c.toString());
                testButton.setBackground(root.getContext().getDrawable(R.drawable.custom_button));
                //Button textButton = (Button)LayoutInflater.from(whatToAdd.getContext()).inflate(R.layout.letter_button, null, false);

                testButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setText(textView.getText() + c.toString());
                    }
                });
                single.addView(testButton);
            }
        }
        relativeLayout.addView(whatToAdd);
        int index = 0;


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    ArrayList<Character> getLetters(String str)
    {
        ArrayList<Character> toReturn = new ArrayList<>();
        for(int i = 0; i < str.length(); i++)
        {
            if(Character.isLetter(str.charAt(i)))
                toReturn.add(str.charAt(i));
        }
        double difficulty = 0.2;
        int extraChars = (int) Math.ceil(toReturn.size() * difficulty);
        Random random = new Random();
        toReturn.add(Character.valueOf((char) (random.nextInt(26) + 65)));
        return toReturn;
    }

}