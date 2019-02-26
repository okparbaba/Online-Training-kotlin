package com.greenhacker.greenhackeronlinetraining.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.models.Posts
import com.nostra13.universalimageloader.core.ImageLoader

class RecyclerViewAdapter(internal var list: List<Posts>, internal var imageLoader: ImageLoader) : RecyclerView.Adapter<RecyclerViewAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val posts = list[position]
        holder.postTitle.text = posts.postTitle
        holder.postBody.text = posts.postBody
        val image1 = posts.url
        imageLoader.displayImage(image1, holder.postImageView)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var postTitle: TextView
        internal var postBody: TextView
        internal var postImageView: ImageView

        init {
            postImageView = itemView.findViewById<View>(R.id.post_imageView) as ImageView
            postTitle = itemView.findViewById<View>(R.id.post_title) as TextView
            postBody = itemView.findViewById<View>(R.id.post_body) as TextView
        }
    }
}
