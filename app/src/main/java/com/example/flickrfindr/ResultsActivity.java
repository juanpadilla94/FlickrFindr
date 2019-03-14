package com.example.flickrfindr;

import android.content.Intent;
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
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        // key: Photo title : Value URL of Photo
        LinkedHashMap<String, String> queryMap
                = PhotoFactory.parsePhotos(getIntent().getStringExtra("query"));
        LinearLayout linearLay = (LinearLayout)findViewById(R.id.linearLayout);
        boolean colorFlip = true;
        // Iterate through all photos and allow full size photo when tapped on image/text
        for(String photoKey : queryMap.keySet()) {
            final ImageView flickrImage = new ImageView(this);
            try {
                // image: Shows full size photo when tapped
                final Bitmap photoBitMap = new BitMapFactory().execute(queryMap.get(photoKey)).get();
                flickrImage.setImageBitmap(photoBitMap);
                flickrImage.setMinimumHeight(200);
                flickrImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoIntent =
                                new Intent(ResultsActivity.this, PhotoActivity.class);
                        photoIntent.putExtra("photo", photoBitMap);
                        startActivity(photoIntent);
                    }
                });
                // text: Shows full size photo when tapped
                TextView photoTitle = new TextView(this);
                photoTitle.setText(photoKey);
                photoTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoIntent =
                                new Intent(ResultsActivity.this, PhotoActivity.class);
                        photoIntent.putExtra("photo", photoBitMap);
                        startActivity(photoIntent);
                    }
                });
                CardView photoCard = new CardView(this);
                photoCard.setMinimumHeight(200);
                // Changes color of card every other time
                if(colorFlip) {
                    photoCard.setCardBackgroundColor(Color.parseColor("#FFFFA727"));
                    photoTitle.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else {
                    photoCard.setCardBackgroundColor(Color.parseColor("#ffffff"));
                    photoTitle.setTextColor(Color.parseColor("#000000"));
                }
                colorFlip = !colorFlip;
                photoCard.addView(flickrImage,
                        new ViewGroup.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT));
                photoCard.addView(photoTitle);
                photoTitle.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                linearLay.addView(photoCard);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
