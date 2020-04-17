package com.telstra.telstra_poc.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.telstra.telstra_poc.R
import com.telstra.telstra_poc.adapter.MyAdapter
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    // Creating ArrayList to store rowtitles,descriptions,imageurls
    private var rowtitles = ArrayList<String>()
    private var descriptions = ArrayList<String>()
    private var imageUrls = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val swipeRefreshLayout = findViewById(R.id.simpleSwipeRefreshLayout) as SwipeRefreshLayout
        // get the reference of RecyclerView that was created in the MainActivity
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        // set a LinearLayoutManager with default vertical orientation
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        try {
            // since we have JSON object, so we are getting the object
            //here we are calling a function and that function is returning the JSON object
            val obj = JSONObject(loadJSONFromAsset())
            supportActionBar?.title = obj.getString("title")
            // fetch JSONArray named rows by using getJSONArray
            val rowArray = obj.getJSONArray("rows")
          //  val userArray = obj.getJSONArray("users")
            // implement for loop for getting row data ie. rowtitles,description,imageurls
            for (i in 0 until rowArray.length()) {
                    // create a JSONObject for fetching single row data
                    val rowDetail = rowArray.getJSONObject(i)
                    // fetch rowtitle,description,imageurl store it in arraylist
                    if (rowDetail.getString("title").equals("null"))
                    rowtitles.add("No Title")
                else
                        rowtitles.add(rowDetail.getString("title"))

                if (rowDetail.getString("description").equals("null"))
                    descriptions.add("No Description")
                else
                    descriptions.add(rowDetail.getString("description"))
                    imageUrls.add(rowDetail.getString("imageHref"))
            }
        } catch (e: JSONException) {
            //exception
            e.printStackTrace()
        }

        //  call the constructor of MyAdapter to send the reference and data to Adapter
        val customAdapter = MyAdapter(this@MainActivity, rowtitles, descriptions, imageUrls)
        recyclerView.adapter = customAdapter // set the Adapter to RecyclerView

        swipeRefreshLayout.setOnRefreshListener { // cancel the Visual indication of a refresh
            swipeRefreshLayout.isRefreshing = false
            //shuffleItems()
        }
    }
    fun shuffleItems() {
        // shuffle the ArrayList's items and set the adapter

        // Collections.shuffle(personImages, Random(System.currentTimeMillis()))
        // call the constructor of CustomAdapter to send the reference and data to Adapter
       // val customAdapter = MyAdapter(this@MainActivity, rowtitles, descriptions, imageUrls)
        //recyclerView.adapter = customAdapter // set the Adapter to RecyclerView

    }
    private fun loadJSONFromAsset(): String? {
        //function to load the JSON from the Asset and return the object
        var json: String?
        val charset: Charset = Charsets.UTF_8
        try {

           // val `is` = assets.open("example.json")
            val `is` = assets.open("telstra.json")

            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}