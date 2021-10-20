package com.example.simplerecyclerviewfromapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var rvMain : RecyclerView
    var names = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain =findViewById(R.id.rvMain)
        rvMain.adapter = NamesAdapter(names)
        rvMain.layoutManager = LinearLayoutManager(this)

        requestApi()
    }

    private fun requestApi() {

        CoroutineScope(Dispatchers.IO).launch {

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
        withContext(Dispatchers.Main) {

            val jsonObject = JSONArray(data)

            for ( i in 0 until jsonObject.length()){
                var name = jsonObject.getJSONObject(i).getString("name").toString()
                names.add("${i+1}: $name \n")
                rvMain.adapter?.notifyDataSetChanged()
            }
        }

    }
}