package com.example.flickrfindr;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        LinkedHashMap<String, String> queryMap
                = PhotoFactory.parsePhotos(getIntent().getStringExtra("query"));
        LinearLayout linearLay = (LinearLayout)findViewById(R.id.linearLayout);
        boolean colorFlip = true;
        for(String photoKey : queryMap.keySet()) {
            // IMAGE
            ImageView flickrImage = new ImageView(this);
            Bitmap photoBitMap = null;
            try {
                photoBitMap = new BitMapFactory().execute(queryMap.get(photoKey)).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flickrImage.setImageBitmap(photoBitMap);
            flickrImage.setMinimumHeight(200);
            flickrImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
            // TITLE
            TextView photoTitle = new TextView(this);
            photoTitle.setText(photoKey);
            CardView photoCard = new CardView(this);
            photoCard.setMinimumHeight(200);
            photoCard.setCardBackgroundColor(Color.parseColor("#FF0000"));
            if(colorFlip) photoCard.setCardBackgroundColor(Color.parseColor("#002aff"));
            colorFlip = !colorFlip;
            photoCard.addView(flickrImage);
            photoCard.addView(photoTitle);
            linearLay.addView(photoCard);
        }
    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener cardListener = new View.OnClickListener() {
        public void onClick(View v) {
            System.out.println("CLICKED CARD");
        }
    };
}
