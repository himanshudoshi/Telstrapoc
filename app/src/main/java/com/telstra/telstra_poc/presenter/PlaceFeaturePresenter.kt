package com.telstra.telstra_poc.presenter

import android.content.Context
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.google.gson.GsonBuilder
import com.telstra.telstra_poc.PlaceFeatureContractor
import com.telstra.telstra_poc.R
import com.telstra.telstra_poc.model.PlaceFeatureData
import org.json.JSONException

/** class work as a manager between view and model . */

class PlaceFeaturePresenter :
    PlaceFeatureContractor.IMainPresenter {
    private var mListItemView: PlaceFeatureContractor.IMainView? = null
    private var mContext: Context? = null

    /** @method for attaching view  **/
    override fun attachView(view: PlaceFeatureContractor.IMainView) {
        mListItemView = view
        mContext = view.getActivityContext()
    }

    /** @method releasing view by assigning null **/
    override fun detachView() {
        mListItemView = null
    }

    /** @Method data request call. **/
    override fun getListItems() {
        try {
            val gson = GsonBuilder().create()
            val listData = gson.fromJson(loadJSONFromAsset, PlaceFeatureData::class.java)
            mListItemView?.showListDetails(listData)
        } catch (e: JSONException) {
            //exception
            makeText(mContext, mContext?.getString(R.string.noresponse), LENGTH_SHORT)?.show()
        }
    }
      /**   Read Json from Asset Folder **/
    private val loadJSONFromAsset: String?
        get() {
            return mContext?.assets?.open("placefeature.json")?.bufferedReader()
                ?.use { it.readText() }
        }

}