package com.example.flickrfindr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitMapFactory extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL photoUrl = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) photoUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap photoBitmap = BitmapFactory.decodeStream(input);
            return photoBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
