@file:Suppress("SpellCheckingInspection")

package com.telstra.telstra_poc.model

import com.google.gson.annotations.SerializedName

/** data class to store data and to fetch the data.similar to plain object java class(POJO) */

data class PlaceFeatureData(
    @field:SerializedName("title")
    var title: String? = null,
    @SerializedName("rows")
    val listItem: List<PlaceFeatureItem>
)

data class PlaceFeatureItem(
    @field:SerializedName("title")
    var title: String? = null,
    @field:SerializedName("description")
    var description: String? = null,
    @field:SerializedName("imageHref")
    var imageHref: String? = null
)

