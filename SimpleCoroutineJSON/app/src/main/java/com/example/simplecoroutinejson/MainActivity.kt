package com.example.simplecoroutinejson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var etIndex: EditText
    lateinit var submit: Button
    lateinit var tvName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etIndex = findViewById(R.id.etIndex)
        submit = findViewById(R.id.submit)
        tvName = findViewById(R.id.tvName)

        submit.setOnClickListener { requestApi() }
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

        var response = URL("https://dojo-recipes.herokuapp.com/contacts/").readText(Charsets.UTF_8)

        return response

    }

    private suspend fun updateText(data:String) {
        withContext(Dispatchers.Main) {

            val jsonArray = JSONArray(data)

            var searchName = jsonArray.getJSONObject(etIndex.text.toString().toInt()).getString("name")
            tvName.text = searchName

        }

    }
}