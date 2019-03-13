package com.example.flickrfindr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // PHOTOS WILL BE SEARCHED BY TEXT (WHICH CONTAIN TAGS;
    // ALTHOUGH THEY CAN BE SEARCHED EXCLUSIVELY BY TAGS AMONG OTHER PARAMETERS
    // https://www.flickr.com/services/api/flickr.photos.search.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button searchButton = (Button)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, ResultsActivity.class);
                EditText editQuery = (EditText)findViewById(R.id.edit_query);
                searchIntent.putExtra("query", editQuery.getText().toString());
                startActivity(searchIntent);
            }
        });
    }
}
