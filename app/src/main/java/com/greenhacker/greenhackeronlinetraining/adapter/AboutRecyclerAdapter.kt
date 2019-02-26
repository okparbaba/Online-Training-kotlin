package com.greenhacker.greenhackeronlinetraining.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class AboutRecyclerAdapter : RecyclerView.Adapter<AboutRecyclerAdapter.MyHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        return MyHolder(viewGroup)
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
