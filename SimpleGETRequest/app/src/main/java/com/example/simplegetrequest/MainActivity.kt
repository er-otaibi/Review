package com.example.simplegetrequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var tvText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvText = findViewById(R.id.tvText)

        requestApi()
    }

    private fun requestApi() {

        CoroutineScope(IO).launch {

            val data = async {

                fetchAllNames()

            }.await()

            if (data.isNotEmpty())
            {

                updateText(data)
            }

        }

    }

    private fun fetchAllNames():String{

        var response = URL("https://dojo-recipes.herokuapp.com/people/?format=json").readText(Charsets.UTF_8)

        return response

    }

    private suspend fun updateText(data:String) {
        withContext(Main) {

            val jsonArray = JSONArray(data)

            for ( i in 0 until jsonArray.length()){
                var name = jsonArray.getJSONObject(i).getString("name")
                tvText.append("${i+1}: $name \n")
            }

        }

    }
}