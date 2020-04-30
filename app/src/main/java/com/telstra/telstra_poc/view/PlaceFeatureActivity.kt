@file:Suppress("SpellCheckingInspection")

package com.telstra.telstra_poc.view

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
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
    override fun getPlaceFeatureItem() {
        mPlaceFeaturePresenter.getListItems()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(): Boolean {
       val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        val networkCapabilities= cm.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
      //  return cm.isActiveNetworkMetered

    }

}