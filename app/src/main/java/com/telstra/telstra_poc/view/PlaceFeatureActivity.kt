@file:Suppress("SpellCheckingInspection")

package com.telstra.telstra_poc.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.telstra.telstra_poc.PlaceFeatureContractor
import com.telstra.telstra_poc.R
import com.telstra.telstra_poc.adapter.PlaceFeaturesDataAdapter
import com.telstra.telstra_poc.model.PlaceFatureData
import com.telstra.telstra_poc.presenter.PlaceFeaturePresenter
import kotlinx.android.synthetic.main.activity_main.*

/**  Min class to show PlaceFeaturedata  **/
class PlaceFeatureActivity : AppCompatActivity(), PlaceFeatureContractor.IMainView {
    private lateinit var mPlaceFeaturePresenter: PlaceFeaturePresenter
    private var customAdapter: PlaceFeaturesDataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attachPresenter()
        getPlaceFeatureItem()
        swipeRefreshLayout.setOnRefreshListener {
            getPlaceFeatureItem()
        }
    }
    /** @Method return application context  . */

    override fun getActivityContext(): Context? {
        return applicationContext
    }

    override fun showListDetails(listData: PlaceFatureData) {
        customAdapter = PlaceFeaturesDataAdapter(this, listData)
        supportActionBar?.title = listData.title
        recyclerView?.layoutManager =
            LinearLayoutManager(getActivityContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = customAdapter
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


}