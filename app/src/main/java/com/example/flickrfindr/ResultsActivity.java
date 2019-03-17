package com.example.flickrfindr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        showResults(getIntent().getStringExtra("type"));
    }

    private void showResults(String type) {
        // key: Photo title  value: URL of Photo
        LinkedHashMap<String, String> resultsMap;
        if("search".equals(type)) {
            resultsMap = PhotoFactory.parsePhotos(getIntent().getStringExtra("query"));
        }
        else {
            resultsMap = new LinkedHashMap<>();
            bookmarkResults(resultsMap);
        }
        LinearLayout linearLay = (LinearLayout)findViewById(R.id.linearLayout);
        boolean colorFlip = false;
        // Iterate through all photos and allow full size photo when tapped on image/text
        for(String photoUrl : resultsMap.keySet()) {
            final ImageView flickrImage = new ImageView(this);
            try {
                // image: Shows full size photo when tapped
                final Bitmap photoBitMap = new BitMapFactory().execute(photoUrl).get();
                flickrImage.setImageBitmap(photoBitMap);
                flickrImage.setMinimumHeight(200);
                // text: Shows full size photo when tapped
                TextView photoTitle = new TextView(this);
                photoTitle.setText(resultsMap.get(photoUrl));
                CardView photoCard = new CardView(this);
                photoCard.setMinimumHeight(200);
                // Changes color of card every other time
                if(colorFlip = !colorFlip) {
                    photoCard.setCardBackgroundColor(Color.parseColor("#FFFFA727"));
                    photoTitle.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else {
                    photoCard.setCardBackgroundColor(Color.parseColor("#ffffff"));
                    photoTitle.setTextColor(Color.parseColor("#000000"));
                }
                photoCard.addView(flickrImage,
                        new ViewGroup.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT));
                photoCard.addView(photoTitle);
                photoTitle.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                linearLay.addView(photoCard);
                final Intent photoIntent =
                        new Intent(ResultsActivity.this, PhotoActivity.class);
                photoIntent.putExtra("url", photoUrl);
                photoIntent.putExtra("title", resultsMap.get(photoUrl));
                photoIntent.putExtra("query", getIntent().getStringExtra("query"));
                photoIntent.putExtra("photo", photoBitMap);
                flickrImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Cruz");
                        startActivity(photoIntent);
                    }
                });
                photoTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { startActivity(photoIntent);
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void bookmarkResults(LinkedHashMap<String, String> resultsMap) {
        SharedPreferences bookmarkStore = this.getSharedPreferences(
                "bookmarkStore2", Context.MODE_PRIVATE);
        Set<String> urlSet = bookmarkStore.getStringSet("bookmarks2", null);
        if(urlSet == null) { urlSet = new HashSet<>(); }
        for(String bookmark : urlSet) {
            int endIndex = bookmark.indexOf(".jpg") + 5;
            String photoUrl = bookmark.substring(0, endIndex);
            String photoTitle = bookmark.substring(endIndex);
            resultsMap.put(photoUrl, photoTitle);
        }
    }
}
