package com.greenhacker.greenhackeronlinetraining.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.api.APIService
import com.greenhacker.greenhackeronlinetraining.api.APIUrl
import com.greenhacker.greenhackeronlinetraining.models.BlogDetail
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass.
 */
class BLogDetailFragment : Fragment() {
    internal var bLogDetail: BlogDetail? = null
    internal lateinit var tvblogTitle: TextView
    internal lateinit var tvblogDescription: TextView
    internal lateinit var tvblogAdminName: TextView
    internal lateinit var imgView: ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Main Category"

        val config = ImageLoaderConfiguration.Builder(activity!!).build()
        ImageLoader.getInstance().init(config)
        tvblogTitle = view.findViewById(R.id.tvBlogDetailTitle)
        tvblogDescription = view.findViewById(R.id.tvBlogDetailDescription)
        tvblogAdminName = view.findViewById(R.id.tvBlogDetailAdminName)
        imgView = view.findViewById(R.id.blogDetailImage)

        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val id = arguments!!.getInt("blog_id")
        val service = retrofit.create<APIService>(APIService::class.java!!)
        val call = service.getBlogDetail(id)
        call.enqueue(object : Callback<BlogDetail> {
            override fun onResponse(call: Call<BlogDetail>, response: Response<BlogDetail>) {
                bLogDetail = BlogDetail()
                bLogDetail = response.body()
                val blogTitle = bLogDetail!!.title
                val blogDescription = bLogDetail!!.description
                val blogAdminName = bLogDetail!!.admin_name
                val imagUrl = bLogDetail!!.imageUrl
                val imageLoader = ImageLoader.getInstance()
                imageLoader.displayImage(imagUrl, imgView)

                tvblogTitle.text = blogTitle
                tvblogDescription.text = blogDescription
                tvblogAdminName.text = blogAdminName

            }

            override fun onFailure(call: Call<BlogDetail>, t: Throwable) {

            }
        })

    }

}// Required empty public constructor
