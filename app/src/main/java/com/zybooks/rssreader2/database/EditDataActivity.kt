package com.zybooks.rssreader2.database


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zybooks.rssreader2.R


class EditDataActivity : AppCompatActivity() {
    private var btnSave: Button? = null
    private var btnDelete: Button? = null
    private var editable_item: EditText? = null
    var mDatabaseHelper: DatabaseHelper? = null
    private var selectedName: String? = null
    private var selectedID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_data_layout)
        btnSave = findViewById<View>(R.id.btnSave) as Button
        btnDelete = findViewById<View>(R.id.btnDelete) as Button
        editable_item = findViewById<View>(R.id.editable_item) as EditText
        mDatabaseHelper = DatabaseHelper(this)

        // get intent extra from ListDataActivity
        val receivedIntent = intent
        selectedID = receivedIntent.getIntExtra("id", -1)
        selectedName = receivedIntent.getStringExtra("name")

        // show current selected name
        editable_item!!.setText(selectedName)
        btnSave!!.setOnClickListener { // Check to make sure value is not null
            val item = editable_item!!.text.toString()
            if (item != "") {
                mDatabaseHelper!!.updateName(item, selectedID, selectedName!!)
            } else {
                toastMessage("You must enter a name")
            }
        }
        btnDelete!!.setOnClickListener {
            mDatabaseHelper!!.deleteName(selectedID, selectedName!!)
            editable_item!!.setText("")
            toastMessage("removed from database")
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "EditDataActivity"
    }
}
