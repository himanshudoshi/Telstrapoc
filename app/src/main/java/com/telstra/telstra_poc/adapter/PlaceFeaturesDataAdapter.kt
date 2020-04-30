package com.telstra.telstra_poc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.*
import com.telstra.telstra_poc.R
import com.telstra.telstra_poc.model.PlaceFeatureData
import kotlinx.android.synthetic.main.item_layout.view.*
import java.net.UnknownHostException

class PlaceFeaturesDataAdapter(private var context: Context,private var listData: PlaceFeatureData) : RecyclerView.Adapter<PlaceFeaturesDataAdapter.PlaceFeatureViewHolder>() {
    
    /** @Method initializing view and returning/inflating view  . */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceFeatureViewHolder {
        // inflate the Layout with the UI that we have created for the RecyclerView
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.item_layout, parent, false)
        return PlaceFeatureViewHolder(v)
    }
    /** @Method data binding in each element . */

    override fun onBindViewHolder(holder: PlaceFeatureViewHolder, position: Int) {
        // set the data in items of the RecyclerView
        try {
            when {
                !listData.listItem[position].title.isNullOrEmpty() -> holder.rowtitle?.text = listData.listItem[position].title
                else -> holder.rowtitle?.text = context.getString(R.string.nodata)
            }
            when {
                !listData.listItem[position].description.isNullOrEmpty() -> holder.descriptions?.text = listData.listItem[position].description
                else -> holder.descriptions?.text = context.getString(R.string.nodescription)
            }
            when {
                !listData.listItem[position].imageHref.isNullOrEmpty() ->

                        with(holder.imageurls.context)
                            .load(listData.listItem[position].imageHref)
                            .centerCrop()
                            .placeholder(R.drawable.ic_image_place_holder)
                            .error(R.drawable.ic_broken_image)
                            .fallback(R.drawable.ic_no_image)
                            .into(holder.imageurls)

            }
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.noresponse), Toast.LENGTH_SHORT)?.show()        }
    }
    /** @Method return item count . */

    override fun getItemCount(): Int {
        return when {
            !listData.listItem.isNullOrEmpty() -> listData.listItem.size
            else -> 0
        }
    }
    /** @Method return view holder . */

    class PlaceFeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rowtitle: TextView? = itemView.rowtitle
        var descriptions: TextView? = itemView.descriptions
        var imageurls: ImageView = itemView.imageUrl
    }

}

