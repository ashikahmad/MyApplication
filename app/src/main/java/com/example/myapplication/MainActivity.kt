package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import api.ApiClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
//import okhttp3.*
import java.awt.font.TextAttribute
import java.io.IOException

class MainActivity : AppCompatActivity() {

//    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Picasso.get().load("https://live.staticflickr.com/8511/8470663773_c006416fac_b.jpg").into(imageView);

//        val req = Request.Builder()
//            .url("https://restcountries.eu/rest/v2/all")
//            .build()
//
//        client.newCall(req).enqueue(object : Callback {
//            override fun onResponse(call: Call, response: Response) {
//                runOnUiThread {
//                    textView.text = response.body()?.string()
//                }
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("Response", "Failed")
//            }
//        })

        ApiClient(this).getAllCountries { countries, message ->
            if (countries != null) {
                for (country in countries) {
                    Log.d("Response", country.toJSON())
                }
            }

        }
    }
}
