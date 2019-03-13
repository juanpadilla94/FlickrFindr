package com.example.flickrfindr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // PHOTOS WILL BE SEARCHED BY TEXT (WHICH CONTAIN TAGS;
    // ALTHOUGH THEY CAN BE SEARCHED EXCLUSIVELY BY TAGS AMONG OTHER PARAMETERS
    // https://www.flickr.com/services/api/flickr.photos.search.html
    String apiKey;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        apiKey = "1508443e49213ff84d566777dc211f2a";
        String searchWord = "cr7";
        url = "https://api.flickr.com/services/rest/?method=flickr.photos.search" +
                "&api_key=" + apiKey +
                "&text=" + searchWord +
                "&per_page=25" +
                "&format=json&nojsoncallback=1";
        try {
            String jsonGetResult = new PhotoStore().execute(url).get();
            String jsonPhotos = new JSONObject(jsonGetResult).get("photos").toString();
            String jsonPhoto = new JSONObject(jsonPhotos).get("photo").toString();
            JSONArray jsonArr = new JSONArray(jsonPhoto);
            for(int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonHolder = new JSONObject(jsonArr.get(i).toString());
                System.out.println(jsonHolder.get("id"));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent myIntent = new Intent(MainActivity.this, ResultsActivity.class);
        startActivity(myIntent);
    }
}
