package com.example.flickrfindr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    SharedPreferences queriesStore;
    Spinner queriesSpinner;
    Set<String> queriesSet;

    // PHOTOS WILL BE SEARCHED BY TEXT (WHICH CONTAIN TAGS;
    // ALTHOUGH THEY CAN BE SEARCHED EXCLUSIVELY BY TAGS AMONG OTHER PARAMETERS
    // https://www.flickr.com/services/api/flickr.photos.search.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button searchButton = (Button)findViewById(R.id.search_button);
        quickSearchOptions();
        // Makes Query REQ and shows results
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, ResultsActivity.class);
                EditText editQuery = (EditText)findViewById(R.id.edit_query);
                String query = editQuery.getText().toString();
                // CHECK FOR NULL OR EMPTY VALUES
                if((query == null
                        || query.trim().length() < 1)
                        && ((query = queriesSpinner.getSelectedItem().toString()) == null
                        || query.trim().length() < 1)) {
                    Toast.makeText(MainActivity.this,
                            "Please Enter Valid Input", Toast.LENGTH_SHORT).show();
                }
                else {
                    queriesSet.add(query);
                    queriesStore.edit().putStringSet("queries", queriesSet).apply();
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

    public void quickSearchOptions() {
        queriesStore = this.getSharedPreferences("queriesStore", Context.MODE_PRIVATE);
        queriesSet = queriesStore.getStringSet("queries", new TreeSet<String>());
        final ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(String queryOpt : queriesSet) {
            spinnerAdapter.add(queryOpt);
            spinnerAdapter.notifyDataSetChanged();
        }
        queriesSpinner = (Spinner)findViewById(R.id.queries_spinner);
        queriesSpinner.setAdapter(spinnerAdapter);
    }
}
