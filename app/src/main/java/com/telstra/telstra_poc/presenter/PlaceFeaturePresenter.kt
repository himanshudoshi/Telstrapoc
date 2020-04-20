package com.telstra.telstra_poc.presenter

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.telstra.telstra_poc.PlaceFeatureContractor
import com.telstra.telstra_poc.model.PlaceFatureData
import org.json.JSONException
/** class work as a manager between view and model . */

class PlaceFeaturePresenter :
    PlaceFeatureContractor.IMainPresenter{
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
            val listData = gson.fromJson(loadJSONFromAsset(), PlaceFatureData::class.java)
            mListItemView?.showListDetails(listData)
        }

        catch (e: JSONException) {
            //exception
            Log.e("JsonException", "Exception" + e.printStackTrace())
        }
    }

    fun loadJSONFromAsset(): String? {
        return mContext?.assets?.open("telstra.json")?.bufferedReader()?.use { it.readText() }
    }

}