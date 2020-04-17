package com.telstra.telstra_poc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.telstra.telstra_poc.R

class MyAdapter(private var context: Context,
                private var rowtitles: ArrayList<String>,
                private var descriptions: ArrayList<String>,
                private var imageUrls: ArrayList<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // inflate the Layout with the UI that we have created for the RecyclerView
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items of the RecyclerView

        var rowtitle = rowtitles[position]
        var description = descriptions[position]
        holder.rowtitle?.text = rowtitle
        holder.descriptions?.text = description
    Glide.with(holder.imageurls.context)
    .load(imageUrls[position].toString())
    .centerCrop()
    .placeholder(R.drawable.ic_image_place_holder)
    .error(R.drawable.ic_broken_image)
    .fallback(R.drawable.ic_no_image)
    .into(holder.imageurls)

    }

       override fun getItemCount(): Int {
        //return the item count
        return rowtitles.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var rowtitle: TextView = itemView.findViewById(R.id.rowtitle) as TextView
        internal var descriptions: TextView = itemView.findViewById(R.id.descriptions) as TextView
        internal var imageurls: ImageView = itemView.findViewById(R.id.imageurls) as ImageView

    }
}
