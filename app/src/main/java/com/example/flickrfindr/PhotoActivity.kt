package com.example.flickrfindr

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.*
import java.util.HashSet

// Shows Full-Size Version of Photo that was selected on previous screen
class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        val intent = intent
        val photoTitle = findViewById<View>(R.id.photo_title) as TextView;
        photoTitle.setText(intent.getStringExtra("title"));
        val bitMap = intent.getParcelableExtra<Parcelable>("photo") as Bitmap
        val flickrPhoto = ImageView(this)
        flickrPhoto.setImageBitmap(bitMap)
        val frameLay = findViewById(R.id.frameLayout) as FrameLayout
        frameLay.addView(flickrPhoto)
        // Takes you to main menu
        val searchButton = findViewById<View>(R.id.search_button) as Button
        searchButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val searchIntent = Intent(this@PhotoActivity, MainActivity::class.java)
                startActivity(searchIntent)
            }
        })
        // Takes you to results screen
        val resultsButton = findViewById(R.id.results_button) as Button
        resultsButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val resultsIntent = Intent(this@PhotoActivity, ResultsActivity::class.java)
                if("search" ==intent.getStringExtra("type")) {
                    resultsIntent.putExtra("type", "search");
                    resultsIntent.putExtra("query", intent.getStringExtra("query"))
                    resultsIntent.putExtra("page", intent.getIntExtra("page", 1))
                }
                else {
                    resultsIntent.putExtra("type", "bookmarkSearch");
                }
                startActivity(resultsIntent)
            }
        })
    }

    fun onBookmarkClicked(view: View) {
        if(view is CheckBox) {
            val checked: Boolean = view.isChecked
            if(checked) {
                val bookmarkStore = this.getSharedPreferences(
                        "bookmarkStore2", Context.MODE_PRIVATE)
                var urlSet: MutableSet<String>? = bookmarkStore.getStringSet("bookmarks2", null)
                if (urlSet == null) { urlSet = HashSet() }
                urlSet.add(intent.getStringExtra("url")
                        + " " + intent.getStringExtra("title"))
                bookmarkStore.edit().putStringSet("bookmarks2", urlSet).apply()
                Toast.makeText(this, "Bookmarked Photo!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
