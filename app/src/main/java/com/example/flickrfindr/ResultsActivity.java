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

import java.util.concurrent.ExecutionException;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ImageView flickrImage = new ImageView(this);
        Bitmap photoBitMap = null;
        try {
            photoBitMap = new BitMapFactory().execute("https://farm8.staticflickr.com/7830/32392551677_77d7c3e962_t.jpg").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flickrImage.setImageBitmap(photoBitMap);

        LinearLayout linearLay = (LinearLayout)findViewById(R.id.linearLayout);
        CardView flickrCard = new CardView(this);
        flickrCard.setMinimumHeight(750);
        flickrCard.setCardBackgroundColor(Color.parseColor("#FF0000"));
        flickrCard.addView(flickrImage);
        linearLay.addView(flickrCard);

        CardView secondCard = new CardView(this);
        secondCard.setCardBackgroundColor(Color.parseColor("#002aff"));
        secondCard.setMinimumHeight(750);
        linearLay.addView(secondCard);

        CardView thirdCard = new CardView(this);
        thirdCard.setCardBackgroundColor(Color.parseColor("#FF0000"));
        thirdCard.setMinimumHeight(750);
        linearLay.addView(thirdCard);

        CardView fourthCard = new CardView(this);
        fourthCard.setCardBackgroundColor(Color.parseColor("#002aff"));
        fourthCard.setMinimumHeight(750);
        linearLay.addView(fourthCard);

        /*
        CardView card = (CardView) findViewById(R.id.cardView);
        ImageView image = new ImageView(this);
        Bitmap photoBitMap = null;
        try {
            photoBitMap = new BitMapFactory().execute("https://farm8.staticflickr.com/7830/32392551677_77d7c3e962_t.jpg").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        image.setImageBitmap(photoBitMap);
        card.addView(image);
        //card.setOnClickListener(cardListener);
        */
    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener cardListener = new View.OnClickListener() {
        public void onClick(View v) {
            System.out.println("CLICKED CARD");
        }
    };
}
