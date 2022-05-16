package com.zybooks.rssreader2.database


//import android.R
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.zybooks.rssreader2.R

class ListDataActivity : AppCompatActivity() {
    //var mDatabaseHelper: DatabaseHelper? = null
    lateinit var mDatabaseHelper: DatabaseHelper
    //private var mListView: ListView? = null
    private lateinit var mListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_layout)
        mListView = findViewById<View>(R.id.listView) as ListView
        mDatabaseHelper = DatabaseHelper(this)
        //Log.d(TAG, "let's see" + mDatabaseHelper.getData().toString())
        populateListView()
    }

    private fun populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.")

        //get the data and append to a list
        val data: Cursor = mDatabaseHelper.getData()
        Log.d(TAG, "test" + mDatabaseHelper.getData().toString())
        val listData = ArrayList<String>()

        //Log.d(TAG, "data?" + data.getString(1))
        //while (data.moveToNext()) {
        while (data.moveToNext()) {
                Log.d(TAG, "data?" + data.getString(1))
                // Column 1 is COL2 (name)
                listData.add(data.getString(1))
        }
        Log.d(TAG, listData.toString())


        listData.add("hi")
        val adapter: ListAdapter = ArrayAdapter(this, R.layout.simple_list_item_1, listData) // listData)
        mListView.adapter = adapter

        //set onItemClickListener to ListView
        mListView.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                val name = adapterView.getItemAtPosition(i).toString()
                Log.d(
                    TAG,
                    "onItemClick: You Clicked on$name"
                )
                val data = mDatabaseHelper.getItemID(name)
                var itemID = -1
                while (data.moveToNext()) {
                    itemID = data.getInt(0)
                }
                if (itemID > -1) {
                    Log.d(
                        TAG,
                        "onItemClick: The ID is: $itemID"
                    )
                    val editScreenIntent =
                        Intent(this@ListDataActivity, EditDataActivity::class.java)
                    editScreenIntent.putExtra("id", itemID)
                    editScreenIntent.putExtra("name", name)
                    startActivity(editScreenIntent)
                } else {
                    toastMessage("No ID associated with that name")
                }
            }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "ListDataActivity"
    }
}
