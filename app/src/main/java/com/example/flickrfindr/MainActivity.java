package com.example.flickrfindr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // PHOTOS WILL BE SEARCHED BY TEXT (WHICH CONTAIN TAGS;
    // ALTHOUGH THEY CAN BE SEARCHED EXCLUSIVELY BY TAGS AMONG OTHER PARAMETERS
    // https://www.flickr.com/services/api/flickr.photos.search.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String apiKey = "1508443e49213ff84d566777dc211f2a";
        String searchWord = "cr7";
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search" +
                "&api_key=" + apiKey +
                "&text=" + searchWord +
                "&per_page=25" +
                "&format=json&nojsoncallback=1";
        new PhotoStore().execute(url);
    }
}
