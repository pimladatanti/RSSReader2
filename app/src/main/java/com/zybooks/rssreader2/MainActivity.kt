package com.zybooks.rssreader2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.zybooks.rssreader2.ApiService
import com.zybooks.rssreader2.database.DatabaseHelper
import com.zybooks.rssreader2.database.ListDataActivity
import com.zybooks.rssreader2.model.Item
import com.zybooks.rssreader2.model.Rss
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

//import android.widget.Button as Button


class MainActivity : AppCompatActivity() {
    private var btnAdd: Button? = null
    private var btnViewData: Button? = null
    private var editText: EditText? = null
    private var mDatabaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        

        //textViewResult = findViewById(R.id.text_view_result);
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
        val feedAPI: ApiService = retrofit.create(ApiService::class.java)
        val call: Call<Rss> = feedAPI.rss
        call.enqueue(object : Callback<Rss> {
            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                //Log.d(TAG, "onResponse: feed: " + response.body().toString());
                Log.d(TAG, "onResponse: Server Response: $response")
                val items: List<Item>? = response.body()?.channels?.get(0)?.items
                //List<Rss> rsss = response.body().getChannels();
                Log.d(TAG, "onResponse: feed: " + response.body()?.channels)
                Log.d(TAG, "onResponse: feed: " + items?.get(0)?.mediaContent)

                //ExtractXML extractXML = new ExtractXML(channels.getItems(), "<=");
                //extractXML.start();


                if (items != null) {
                    for (i in items) {
                        i.title?.let { AddData(it) }
                    }
                }
            }

            override fun onFailure(call: Call<Rss>, t: Throwable) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS; " + t.message)
                Toast.makeText(this@MainActivity, "An Error Occurred", Toast.LENGTH_SHORT).show()
            }
        })
        editText = findViewById<View>(R.id.editText) as EditText
        btnAdd = findViewById<View>(R.id.btnAdd) as Button
        btnViewData = findViewById<View>(R.id.btnView) as Button
        mDatabaseHelper = DatabaseHelper(this)
        btnAdd!!.setOnClickListener {
            val newEntry = editText!!.text.toString()
            if (editText!!.length() != 0) {
                AddData(newEntry)
                editText!!.setText("")
            } else {
                toastMessage("You must put something in the text field!")
            }
        }
        btnViewData!!.setOnClickListener {
            val intent = Intent(this@MainActivity, ListDataActivity::class.java)
            startActivity(intent)
        }
    }

    fun AddData(newEntry: String) {
        val insertData: Boolean? = mDatabaseHelper?.addData(newEntry)
        if (insertData == true) toastMessage("Data Successfully Inserted!") else toastMessage("Something went wrong")
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        //private TextView textViewResult;
        private const val TAG = "MainActivity"
        private const val BASE_URL = "https://www.sandiegouniontribune.com/news/"
    }
}