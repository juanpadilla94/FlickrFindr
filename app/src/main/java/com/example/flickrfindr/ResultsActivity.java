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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ResultsActivity extends AppCompatActivity {

    private String type;
    private String query;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        type = getIntent().getStringExtra("type");
        showResults();
        Button mainMenuButton = (Button)findViewById(R.id.main_menu_button);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(mainMenuIntent);
            }
        });
        Button prevPageButton = (Button) findViewById(R.id.prev_page_button);
        Button nextPageButton = (Button) findViewById(R.id.next_page_button);
        prevPageButton.setText("Page " + String.valueOf(page - 1));
        nextPageButton.setText("Page " + String.valueOf(page + 1));
        if(!"search".equals(type)) {
            nextPageButton.setVisibility(View.GONE);
            prevPageButton.setVisibility(View.GONE);
        }
        else if(page < 2) { prevPageButton.setVisibility(View.GONE); }
    }

    private void showResults() {
        // key: Photo title  value: URL of Photo
        LinkedHashMap<String, String> resultsMap;
        if("search".equals(type)) {
            query = getIntent().getStringExtra("query");
            page = getIntent().getIntExtra("page", 1);
            resultsMap = PhotoFactory.parsePhotos(query, page);
        }
        else {
            resultsMap = new LinkedHashMap<>();
            bookmarkResults(resultsMap);
        }
        LinearLayout linearLay = (LinearLayout)findViewById(R.id.linearLayout);
        boolean colorFlip = true;
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
                photoIntent.putExtra("type", type);
                photoIntent.putExtra("page", page);
                photoIntent.putExtra("url", photoUrl);
                photoIntent.putExtra("title", resultsMap.get(photoUrl));
                photoIntent.putExtra("query", query);
                photoIntent.putExtra("photo", photoBitMap);
                flickrImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                "bookmarkStore", Context.MODE_PRIVATE);
        Set<String> urlSet = bookmarkStore.getStringSet("bookmarks", null);
        if(urlSet == null) { urlSet = new HashSet<>(); }
        for(String bookmark : urlSet) {
            int endIndex = bookmark.indexOf(".jpg") + 5;
            String photoUrl = bookmark.substring(0, endIndex);
            String photoTitle = bookmark.substring(endIndex);
            resultsMap.put(photoUrl, photoTitle);
        }
    }

    public void onNextClicked(View view) {
        switch (view.getId()) {
            case R.id.next_page_button:
                page += 1;
                break;
            case R.id.prev_page_button:
                page -= 1;
                break;
        }
        Intent searchIntent = new Intent(ResultsActivity.this, ResultsActivity.class);
        searchIntent.putExtra("type", type);
        searchIntent.putExtra("query", query);
        searchIntent.putExtra("page", page);
        startActivity(searchIntent);
    }
}
