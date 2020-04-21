package com.telstra.telstra_poc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.telstra.telstra_poc.R
import com.telstra.telstra_poc.model.PlaceFeatureData
import kotlinx.android.synthetic.main.item_layout.view.*

class PlaceFeaturesDataAdapter(
    private var context: Context,private var listData: PlaceFeatureData
  ) : RecyclerView.Adapter<PlaceFeaturesDataAdapter.MyViewHolder>() {
    
    /** @Method initializing view and returning/inflating view  . */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // inflate the Layout with the UI that we have created for the RecyclerView
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(v)
    }
    /** @Method data binding in each element . */

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items of the RecyclerView
        if (!listData.listItem[position].title.isNullOrEmpty()) {
            holder.rowtitle?.text = listData.listItem[position].title
        }
        else
        {
            holder.rowtitle?.text = context?.getString(R.string.nodata)
        }
        if (!listData.listItem[position].description.isNullOrEmpty()) {
            holder.descriptions?.text = listData.listItem[position].description
        }
        else
        {
            holder.descriptions?.text = context?.getString(R.string.nodescription)
        }
        if (!listData.listItem[position].imageHref.isNullOrEmpty()) {
            Glide.with(holder.imageurls.context)
                .load(listData.listItem[position].imageHref)
                .centerCrop()
                .placeholder(R.drawable.ic_image_place_holder)
                .error(R.drawable.ic_broken_image)
                .fallback(R.drawable.ic_no_image)
                .into(holder.imageurls)
        }
         }
    /** @Method return item count . */

    override fun getItemCount(): Int {
        if(!listData.listItem.isNullOrEmpty()){
            return listData.listItem.size
        }
        return 0
    }
    /** @Method return view holder . */

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rowtitle: TextView? = itemView.rowtitle
        var descriptions: TextView? = itemView.descriptions
        var imageurls: ImageView = itemView.imageUrl
    }

}

