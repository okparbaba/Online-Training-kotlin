package com.greenhacker.greenhackeronlinetraining.fragment


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.reflect.TypeToken
import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.adapter.BlogPostRecyclerAdapter
import com.greenhacker.greenhackeronlinetraining.api.APIService
import com.greenhacker.greenhackeronlinetraining.api.APIUrl
import com.greenhacker.greenhackeronlinetraining.caches.CacheManager
import com.greenhacker.greenhackeronlinetraining.caches.CacheUtils
import com.greenhacker.greenhackeronlinetraining.caches.M
import com.greenhacker.greenhackeronlinetraining.models.BlogPosts
import com.greenhacker.greenhackeronlinetraining.utils.RecyclerViewAnimation
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

import java.lang.reflect.Type
import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass.
 */
class BlogFragment : Fragment() {
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var blogPostsList: MutableList<BlogPosts>
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    internal lateinit var blogPostRecyclerAdapter: BlogPostRecyclerAdapter
    private var cacheManager: CacheManager? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "BLog "
        recyclerView = view.findViewById(R.id.recycler_BLogView)
        val config = ImageLoaderConfiguration.Builder(activity!!).build()
        ImageLoader.getInstance().init(config)
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container)
        cacheManager = CacheManager(activity!!)
        blogPostsList = ArrayList()
        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<APIService>(APIService::class.java!!)
        val call = service.blogPost
        call.enqueue(object : Callback<List<BlogPosts>> {
            override fun onResponse(call: Call<List<BlogPosts>>, response: Response<List<BlogPosts>>) {

                try {
                    val list = response.body()
                    var blogPosts: BlogPosts? = null
                    for (i in list!!.indices) {
                        blogPosts = BlogPosts()
                        val blogTitle = list[i].title
                        val blogDescription = list[i].description
                        val blogImage = list[i].photo_url
                        val blogId = list[i].id
                        blogPosts.photo_url = blogImage
                        blogPosts.title = blogTitle
                        blogPosts.description = blogDescription
                        blogPosts.id = blogId
                        blogPostsList.add(blogPosts)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                blogPostRecyclerAdapter = BlogPostRecyclerAdapter(blogPostsList, activity, ImageLoader.getInstance())
                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = blogPostRecyclerAdapter
                RecyclerViewAnimation.runLayoutAnimation(recyclerView)
                cacheManager!!.writeJson(blogPostsList, Type::class.java, CacheUtils.RELATED)
                mShimmerViewContainer!!.stopShimmerAnimation()
                mShimmerViewContainer!!.visibility = View.GONE

            }

            override fun onFailure(call: Call<List<BlogPosts>>, t: Throwable) {

            }

        })


    }

    private fun getLatest() {
        if (M.isConnectionAvailable(activity!!)) {

        } else {
            val type = object : TypeToken<ArrayList<BlogPosts>>() {

            }.type
            val itemLatests = cacheManager!!.readJson(type, CacheUtils.LATEST) as ArrayList<BlogPosts>
            if (itemLatests != null)
                blogPostRecyclerAdapter = BlogPostRecyclerAdapter(itemLatests, activity, ImageLoader.getInstance())
            Snackbar.make(view!!, R.string.no_internet_connection, Snackbar.LENGTH_SHORT).setAction(R.string.retry) { getLatest() }.show()
        }


    }


    private fun initFromCache() {
        var blogPostRecyclerAdapter: BlogPostRecyclerAdapter? = null
        val type = object : TypeToken<ArrayList<BlogPosts>>() {

        }.type
        val blogcache = cacheManager!!.readJson(type, CacheUtils.LATEST) as ArrayList<BlogPosts>
        if (blogcache != null)

            blogPostRecyclerAdapter = BlogPostRecyclerAdapter(blogcache, this.activity!!, ImageLoader.getInstance())
        recyclerView.adapter = blogPostRecyclerAdapter
    }

    override fun onResume() {
        super.onResume()
        mShimmerViewContainer!!.startShimmerAnimation()
    }

    override fun onPause() {
        mShimmerViewContainer!!.stopShimmerAnimation()
        super.onPause()
    }
}// Required empty public constructor
