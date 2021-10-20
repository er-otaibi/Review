package com.example.moresimplegetandpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var submit: Button
    lateinit var getRv: Button
    lateinit var rvMain: RecyclerView
    var names = arrayListOf<String>()
    var name=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        submit = findViewById(R.id.submit)
        getRv = findViewById(R.id.getRv)
        rvMain = findViewById(R.id.rvMain)
        rvMain.adapter = NamesAdapter(names)
        rvMain.layoutManager = LinearLayoutManager(this)

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
        getRv.setOnClickListener { getUser() }
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
                    names.add(userName)
                    rvMain.adapter?.notifyDataSetChanged()

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