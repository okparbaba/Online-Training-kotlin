package com.greenhacker.greenhackeronlinetraining.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView

import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.activitys.OrderActivity
import com.greenhacker.greenhackeronlinetraining.fragment.CourseDetailFragment
import com.greenhacker.greenhackeronlinetraining.models.CourseSubCat
import com.greenhacker.greenhackeronlinetraining.utils.MyBounceInterpolator

class SubCatRecyclerAdapter(internal var subCatList: List<CourseSubCat>, internal var ctx: FragmentActivity?) : RecyclerView.Adapter<SubCatRecyclerAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sub_category_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {
        val courseSubCat = subCatList[i]
        myHolder.tvsubCategoryName.text = courseSubCat.category_name
        myHolder.tvsubCategoryTeacherName.text = courseSubCat.teacher_name
        myHolder.tvsubCategoryPrice.text = courseSubCat.price
        myHolder.btOrder.setOnClickListener { v ->
            didTapButton(myHolder.btOrder)
            val i = Intent(v.context, OrderActivity::class.java)
            v.context.startActivity(i)
        }
        myHolder.itemView.setOnClickListener { v ->
            val courseDetailFragment = CourseDetailFragment()
            val args = Bundle()
            val id = courseSubCat.id
            args.putInt("courseDetailId", Integer.parseInt(id!!))
            courseDetailFragment.arguments = args

            val activity = v.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().replace(R.id.container_frame, courseDetailFragment).addToBackStack(null).commit()

            didTapButton(myHolder.itemView)
        }

    }

    override fun getItemCount(): Int {
        return subCatList.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvsubCategoryName: TextView
        internal var tvsubCategoryTeacherName: TextView
        internal var tvsubCategoryPrice: TextView
        internal var btOrder: Button

        init {
            btOrder = itemView.findViewById(R.id.btOrder)
            tvsubCategoryName = itemView.findViewById(R.id.tvsubCategoryName)
            tvsubCategoryTeacherName = itemView.findViewById(R.id.tvsubCategoryTeachcerName)
            tvsubCategoryPrice = itemView.findViewById(R.id.tvsubCategoryPrice)
        }
    }

    fun didTapButton(view: View) {
        val myAnim = AnimationUtils.loadAnimation(ctx, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator

        view.startAnimation(myAnim)
    }
}
