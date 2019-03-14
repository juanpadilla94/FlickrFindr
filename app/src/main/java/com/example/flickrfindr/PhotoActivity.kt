package com.example.flickrfindr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.graphics.Bitmap
import android.widget.FrameLayout
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.ImageView

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        val flickrPhoto = ImageView(this)
        val intent = intent
        val bitMap = intent.getParcelableExtra<Parcelable>("photo") as Bitmap
        flickrPhoto.setImageBitmap(bitMap)
        val frameLay = findViewById(R.id.frameLayout) as FrameLayout
        frameLay.addView(flickrPhoto)
        val searchButton = findViewById<View>(R.id.search_button) as Button
        searchButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val searchIntent = Intent(this@PhotoActivity, MainActivity::class.java)
                startActivity(searchIntent)
            }
        })
        val resultsButton = findViewById(R.id.results_button) as Button
        resultsButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val searchIntent = Intent(this@PhotoActivity, ResultsActivity::class.java)
                startActivity(searchIntent)
            }
        })
    }
}
