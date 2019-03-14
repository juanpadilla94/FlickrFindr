package com.example.flickrfindr

import android.support.v7.app.AppCompatActivity
import android.graphics.Bitmap
import android.widget.FrameLayout
import android.os.Bundle
import android.os.Parcelable
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
    }
}
