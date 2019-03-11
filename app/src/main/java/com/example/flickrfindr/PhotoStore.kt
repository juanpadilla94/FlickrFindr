package com.example.flickrfindr

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL


class PhotoStore : AsyncTask<String, Void, String>() {

    companion object {
        val REQUEST = "GET"
    }

    override fun doInBackground(vararg params: String): String {
        var jsonResult = ""
        var input: String?
        try {
            val searchURL = URL(params[0])
            val httpConnection = searchURL.openConnection() as HttpURLConnection
            // SEND REQ AND CONNECT
            httpConnection.requestMethod = REQUEST;
            httpConnection.connect()
            val stream = InputStreamReader(httpConnection.inputStream)
            val buffReader = BufferedReader(stream as Reader?)
            val stringBuilder = StringBuilder()
            input = buffReader.readLine()
            while (input != null) {
                stringBuilder.append(input)
                input = buffReader.readLine()
            }
            buffReader.close()
            stream.close()
            jsonResult = stringBuilder.toString()
        } catch (e: IOException) {
            print("ERROR");
            e.printStackTrace()
        }
        FeedPhotos(jsonResult);
        return ""
    }
}