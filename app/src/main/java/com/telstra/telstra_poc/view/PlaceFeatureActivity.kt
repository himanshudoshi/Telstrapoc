@file:Suppress("SpellCheckingInspection", "DEPRECATION")

package com.telstra.telstra_poc.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.telstra.telstra_poc.PlaceFeatureContractor
import com.telstra.telstra_poc.R
import com.telstra.telstra_poc.adapter.PlaceFeaturesDataAdapter
import com.telstra.telstra_poc.model.PlaceFeatureData
import com.telstra.telstra_poc.presenter.PlaceFeaturePresenter
import kotlinx.android.synthetic.main.activity_main.*

/**  Min class to show PlaceFeaturedata  **/
class PlaceFeatureActivity : AppCompatActivity(), PlaceFeatureContractor.IMainView {
    private lateinit var mPlaceFeaturePresenter: PlaceFeaturePresenter
    private var featuresDataAdapter: PlaceFeaturesDataAdapter? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.app_name)
        attachPresenter()
        getPlaceFeatureItem()
        swipeRefreshLayout.setOnRefreshListener {
            getPlaceFeatureItem()
        }
    }

    /** @Method return application context  . */

    override fun getActivityContext(): Context? {
        return this
    }

    override fun showListDetails(listData: PlaceFeatureData) {
        featuresDataAdapter = PlaceFeaturesDataAdapter(this, listData)
        when {
            !listData.title.isNullOrEmpty() -> supportActionBar?.title = listData.title
        }
        getActivityContext()?.let {
            featuresDataAdapter = PlaceFeaturesDataAdapter(this, listData)
            recyclerView?.layoutManager =
                LinearLayoutManager(getActivityContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = featuresDataAdapter
            loading.visibility = View.GONE

        }
        swipeRefreshLayout.isRefreshing = false
    }

    /** @Method attaching presenter ,views */
    override fun attachPresenter() {
        mPlaceFeaturePresenter = PlaceFeaturePresenter()
        mPlaceFeaturePresenter.attachView(this)
    }

    /** @Method to initiate data request . */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun getPlaceFeatureItem() {
        if (isNetworkConnected())
        {
            mPlaceFeaturePresenter.getListItems()
        }else
        {
            loading.text =
                getActivityContext()?.getString(R.string.device_not_connected_to_internet)
        }
    }
 @RequiresApi(Build.VERSION_CODES.M)
 private fun isNetworkConnected(): Boolean {

     val connectivityManager =
         getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
     //2
     val activeNetwork = connectivityManager.activeNetwork
     //3
     val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
     //4
     return networkCapabilities != null &&
             networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
 }

}