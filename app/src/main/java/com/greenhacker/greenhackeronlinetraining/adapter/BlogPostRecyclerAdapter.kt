package com.greenhacker.greenhackeronlinetraining.adapter

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.fragment.BLogDetailFragment
import com.greenhacker.greenhackeronlinetraining.models.BlogPosts
import com.greenhacker.greenhackeronlinetraining.utils.MyBounceInterpolator
import com.nostra13.universalimageloader.core.ImageLoader

class BlogPostRecyclerAdapter(internal var blogPostsList: List<BlogPosts>, internal var ctx: FragmentActivity?, internal var imageLoader: ImageLoader) : RecyclerView.Adapter<BlogPostRecyclerAdapter.MyHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.blog_item, viewGroup, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {
        val bPost = blogPostsList[i]
        myHolder.blogPostTitle.text = bPost.title
        val imageUrl = bPost.photo_url
        imageLoader.displayImage(imageUrl, myHolder.blogImageView)

        myHolder.itemView.setOnClickListener { v ->
            val iddd = bPost.id
            didTapButton(myHolder.itemView)
            if (iddd != null) {
                val blogDetail = BLogDetailFragment()
                val args = Bundle()

                args.putInt("blog_id", Integer.parseInt(iddd))
                blogDetail.arguments = args


                val activity = v.context as AppCompatActivity
                activity.supportFragmentManager.beginTransaction().replace(R.id.container_frame, blogDetail).addToBackStack(null).commit()
            }
        }

    }

    override fun getItemCount(): Int {
        return blogPostsList.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var blogPostTitle: TextView
        internal var blogCategoryName: TextView
        internal var blogImageView: ImageView

        init {
            blogImageView = itemView.findViewById(R.id.blogImage)
            blogPostTitle = itemView.findViewById(R.id.tvBlogTitle)
            blogCategoryName = itemView.findViewById(R.id.tvBlogCategoryname)
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
