package com.telstra.telstra_poc

import android.content.Context
import com.telstra.telstra_poc.model.PlaceFeatureData

class PlaceFeatureContractor {

    /** @interface implement by PlaceFeatureActivity */
    interface IMainView {
        fun getActivityContext(): Context?
        fun attachPresenter()
        fun showListDetails(listData: PlaceFeatureData)
        fun getPlaceFeatureItem()
    }

    /** @interface implement by PlaceFeaturePresenter **/
    interface IMainPresenter {
        fun attachView(view: IMainView)
        fun detachView()
        fun getListItems()
    }

}