package com.greenhacker.greenhackeronlinetraining.utils

import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
import com.greenhacker.greenhackeronlinetraining.R

object RecyclerViewAnimation {

    fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        try {
            recyclerView.layoutAnimation = controller
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()

        } catch (e: Exception) {

        }

    }

    fun runLayoutAnimation2(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.rawlayout_animation_slide_right)

        try {
            recyclerView.layoutAnimation = controller
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()

        } catch (e: Exception) {

        }

    }
}
