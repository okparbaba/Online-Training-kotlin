package com.greenhacker.greenhackeronlinetraining.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.activitys.OrderActivity
import com.greenhacker.greenhackeronlinetraining.api.APIService
import com.greenhacker.greenhackeronlinetraining.api.APIUrl
import com.greenhacker.greenhackeronlinetraining.models.CourseDetail
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
class CourseDetailFragment : Fragment() {
    internal var courseDetail: CourseDetail? = null
    internal lateinit var courseImage: ImageView
    internal lateinit var order: Button
    internal lateinit var tvTitle: TextView
    internal lateinit var tvTeacher: TextView
    internal lateinit var tvPrice: TextView
    internal lateinit var tvDescription: TextView
    internal lateinit var tvCourseType: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity!!.title = "CourseDetail"
        return inflater.inflate(R.layout.fragment_course_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        order = view.findViewById(R.id.btOrder2)
        courseImage = view.findViewById(R.id.imagViewCourseDetail)
        tvTitle = view.findViewById(R.id.tvCourseDetailTitle)
        tvPrice = view.findViewById(R.id.tvCourseDetailPrice)
        tvTeacher = view.findViewById(R.id.tvCourseDetailTeacherName)
        tvDescription = view.findViewById(R.id.tvCourseDetailDescription)
        tvCourseType = view.findViewById(R.id.tvCourseDetailType)
        val config = ImageLoaderConfiguration.Builder(activity!!).build()
        ImageLoader.getInstance().init(config)

        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val id = arguments!!.getInt("courseDetailId")
        val service = retrofit.create<APIService>(APIService::class.java!!)
        val call = service.getCourseDetail(id)

        call.enqueue(object : Callback<CourseDetail> {
            override fun onResponse(call: Call<CourseDetail>, response: Response<CourseDetail>) {
                try {
                    courseDetail = CourseDetail()
                    courseDetail = response.body()
                    val imageUrl = courseDetail!!.course_photo_url
                    val imageLoader = ImageLoader.getInstance()
                    imageLoader.displayImage(imageUrl, courseImage)
                    val courseTitle = courseDetail!!.course_name
                    tvTitle.text = courseTitle
                    val coursePrice = courseDetail!!.course_price
                    tvPrice.text = coursePrice
                    val courseTeacherName = courseDetail!!.course_teacher
                    tvTeacher.text = courseTeacherName
                    val courseDescription = courseDetail!!.course_description
                    tvDescription.text = courseDescription
                    val courseType = courseDetail!!.course_type
                    tvCourseType.text = courseType
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<CourseDetail>, t: Throwable) {

            }
        })

        order.setOnClickListener {
            val i = Intent(activity, OrderActivity::class.java)
            startActivity(i)
        }
    }

}// Required empty public constructor
