package com.example.meemshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentMeme: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()

        }

    private fun loadmeme(){
        val imageView=findViewById<ImageView>(R.id.meemImage)
        val progress=findViewById<ProgressBar>(R.id.meemProgress)
        progress.visibility=View.VISIBLE
        // Instantiate the RequestQueue.
        //volley labrariy call the api
        //below statement make request to api and this request are store in queue
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        // its a get request means getting data  from backend
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->

                currentMeme=response.getString("url")
                //using glide labrary to loadimage in image view
                Glide.with(this).load(currentMeme).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        progress.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                    }
                }).into(imageView)

            },
            //below statment handle error
            { error ->
                // TODO: Handle error
            }
        )

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    fun shareMeem(view: View) {
         val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this cool meme I got from Reddit ${currentMeme}")
        val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)

    }
    fun nextMeem(view: View) {
        loadmeme()
    }
}