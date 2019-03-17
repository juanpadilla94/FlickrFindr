package com.example.flickrfindr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // PHOTOS WILL BE SEARCHED BY TEXT (WHICH CONTAIN TAGS;
    // ALTHOUGH THEY CAN BE SEARCHED EXCLUSIVELY BY TAGS AMONG OTHER PARAMETERS
    // https://www.flickr.com/services/api/flickr.photos.search.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button searchButton = (Button)findViewById(R.id.search_button);
        // Makes Query Req and shows results
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, ResultsActivity.class);
                EditText editQuery = (EditText)findViewById(R.id.edit_query);
                String query = editQuery.getText().toString();
                if(query == null || query.trim().length() < 1) {
                    Toast.makeText(MainActivity.this,
                            "Please Enter Non-Empty Query (:", Toast.LENGTH_SHORT).show();
                }
                else {
                    searchIntent.putExtra("type", "search");
                    searchIntent.putExtra("query", query);
                    searchIntent.putExtra("page", 1);
                    startActivity(searchIntent);
                }
            }
        });
    }

    public void onBookmarkClicked(View view) {
        Intent searchIntent = new Intent(MainActivity.this, ResultsActivity.class);
        searchIntent.putExtra("type", "bookmarkSearch");
        startActivity(searchIntent);
    }
}
