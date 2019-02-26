package com.greenhacker.greenhackeronlinetraining.adapter

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView

import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.fragment.CourseSubFragment
import com.greenhacker.greenhackeronlinetraining.models.CoruseMainCat
import com.greenhacker.greenhackeronlinetraining.utils.MyBounceInterpolator

import java.util.ArrayList

class MainCatRecyclerAdapter(internal var mainCatList: MutableList<CoruseMainCat>, internal var ctx: FragmentActivity?) : RecyclerView.Adapter<MainCatRecyclerAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_category_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {
        val coruseMainCat = mainCatList[i]

        myHolder.tvMainCat.text = coruseMainCat.category_name
        myHolder.itemView.setOnClickListener { v ->
            //Toast.makeText(v.getContext(),"RecyclerClick"+i,Toast.LENGTH_LONG).show();


            didTapButton(myHolder.itemView)
            val courseSubFragment = CourseSubFragment()
            val args = Bundle()
            val idd = coruseMainCat.id
            args.putInt("course_id", Integer.parseInt(idd!!))
            courseSubFragment.arguments = args

            val activity = v.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().replace(R.id.container_frame, courseSubFragment).addToBackStack(null).commit()
        }
    }


    override fun getItemCount(): Int {
        return mainCatList.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvMainCat: TextView

        init {
            tvMainCat = itemView.findViewById(R.id.tv_mainCat)


        }
    }

    fun didTapButton(view: View) {
        val myAnim = AnimationUtils.loadAnimation(ctx, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator

        view.startAnimation(myAnim)
    }

    fun setFilter(newList: ArrayList<CoruseMainCat>) {
        mainCatList = ArrayList()
        mainCatList.addAll(newList)
        notifyDataSetChanged()
    }
}
