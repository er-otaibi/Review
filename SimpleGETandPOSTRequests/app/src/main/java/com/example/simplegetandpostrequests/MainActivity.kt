package com.example.simplegetandpostrequests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var tvName: TextView
    lateinit var submit: Button
    lateinit var show: Button
    //lateinit var list: ListView
    var name =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        tvName = findViewById(R.id.tvName)
        submit =findViewById(R.id.submit)
        show = findViewById(R.id.show)
       // list = findViewById(R.id.list)

        submit.setOnClickListener {

            if (etName.text.isEmpty()) {
                Toast.makeText(this, "Please Enter a name" , Toast.LENGTH_SHORT).show()
            }else {
                name = etName.text.toString()
                addUser()
                etName.setText("")
                Toast.makeText(this, "Name is added Successfully" , Toast.LENGTH_SHORT).show()
            }

        }

        show.setOnClickListener { getUser() }
    }
    private fun getUser(){
        val apiInterface = APIClient.getClient()?.create(APIInterface::class.java)

        apiInterface?.getUser()?.enqueue(object : Callback<List<NamesItem>> {
            override fun onResponse(
                call: Call<List<NamesItem>>,
                response: Response<List<NamesItem>>
            ) {

                val resource = response.body()!!
                for ( i in resource){

                    var userName = i.name
                    tvName.append("$userName \n")

                }

            }

            override fun onFailure(call: Call<List<NamesItem>>, t: Throwable) {
                call.cancel()
            }
        })

    }
    private fun addUser(){
        val apiInterface = APIClient.getClient()?.create(APIInterface::class.java)


        apiInterface?.addUser(NamesItem(name, 0))?.enqueue(object :
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