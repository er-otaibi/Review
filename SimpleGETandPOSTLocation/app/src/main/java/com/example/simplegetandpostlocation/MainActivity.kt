package com.example.simplegetandpostlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etLocation: EditText
    lateinit var submit: Button
    lateinit var etSearch: EditText
    lateinit var search: Button
    lateinit var textView: TextView
    var name=""
    var sName=""
    var location=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)
        submit = findViewById(R.id.submit)
        etSearch = findViewById(R.id.etSearch)
        search = findViewById(R.id.search)
        textView = findViewById(R.id.textView)

        submit.setOnClickListener {
            if (etName.text.isEmpty() || etLocation.text.isEmpty()) {
                Toast.makeText(this, "Please Enter a name and location" , Toast.LENGTH_LONG).show()
            }else {
                name = etName.text.toString()
                location = etLocation.text.toString()
                addUser()
                etName.setText("")
                etLocation.setText("")
                Toast.makeText(this, "User is added Successfully" , Toast.LENGTH_SHORT).show()
            }
        }

        search.setOnClickListener {
            if ( etSearch.text.isEmpty()){
                Toast.makeText(this, "Please Enter a name" , Toast.LENGTH_SHORT).show()
            }else {
                sName = etSearch.text.toString()
                getUser()
                etSearch.setText("")

            }

        }
    }

    private fun getUser() {
        val apiInterface = APIClient.getClient()?.create(APIInterface::class.java)

        apiInterface?.getUser()?.enqueue(object : Callback<List<NamesItem>> {
            override fun onResponse(
                call: Call<List<NamesItem>>,
                response: Response<List<NamesItem>>
            ) {

                val resource = response.body()!!
                for ( i in resource){

                   if (i.name == sName){
                   textView.text ="$sName Lives in ${i.location}"
                   }

                }

            }

            override fun onFailure(call: Call<List<NamesItem>>, t: Throwable) {
                call.cancel()
            }
        })

    }
    private fun addUser(){
        val apiInterface = APIClient.getClient()?.create(APIInterface::class.java)


        apiInterface?.addUser(NamesItem( location,name,0))?.enqueue(object :
            Callback<List<NamesItem>> {

            override fun onFailure(call: Call<List<NamesItem>>, t: Throwable) {
                call.cancel()
            }

            override fun onResponse(
                call: Call<List<NamesItem>>,
                response: Response<List<NamesItem>>
            ) {

                response.body()!!
            }

        }
        )
    }
}