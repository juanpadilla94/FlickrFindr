package com.example.flickrfindr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        FrameLayout frameLay = (FrameLayout) findViewById(R.id.frameLayout);
        Intent intent = getIntent();
        ImageView flickrImage = new ImageView(this);
        Bitmap bitMap = (Bitmap) intent.getParcelableExtra("photo");
        flickrImage.setImageBitmap(bitMap);
        frameLay.addView(flickrImage);
    }
}
