package com.greenhacker.greenhackeronlinetraining.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.adapter.SubCatRecyclerAdapter
import com.greenhacker.greenhackeronlinetraining.api.APIService
import com.greenhacker.greenhackeronlinetraining.api.APIUrl
import com.greenhacker.greenhackeronlinetraining.models.CourseSubCat
import com.greenhacker.greenhackeronlinetraining.utils.RecyclerViewAnimation

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class CourseSubFragment : Fragment() {
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var courseSubCatList: MutableList<CourseSubCat>
    internal lateinit var subCatRecyclerAdapter: SubCatRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_sub, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "All Courses"
        recyclerView = view.findViewById(R.id.subCategoryRecycler)
        courseSubCatList = ArrayList()


        val id = arguments!!.getInt("course_id")

        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<APIService>(APIService::class.java!!)
        val call = service.getCourseSubCat(id)
        call.enqueue(object : Callback<List<CourseSubCat>> {
            override fun onResponse(call: Call<List<CourseSubCat>>, response: Response<List<CourseSubCat>>) {
                val list = response.body()
                var courseSubCat: CourseSubCat? = null
                for (i in list!!.indices) {
                    courseSubCat = CourseSubCat()
                    val subCategoryId = list[i].id
                    val subCategoryName = list[i].category_name
                    val subCategoryTeacherName = list[i].teacher_name
                    val subCategoryPrice = list[i].price
                    val subCategoryDescription = list[i].description
                    val subCategoryType = list[i].type
                    courseSubCat.id = subCategoryId
                    courseSubCat.category_name = subCategoryName
                    courseSubCat.teacher_name = subCategoryTeacherName
                    courseSubCat.price = subCategoryPrice
                    courseSubCat.description = subCategoryDescription
                    courseSubCat.type = subCategoryType
                    courseSubCatList.add(courseSubCat)
                }
                subCatRecyclerAdapter = SubCatRecyclerAdapter(courseSubCatList, activity)
                recyclerView.layoutManager = LinearLayoutManager(activity)
                RecyclerViewAnimation.runLayoutAnimation2(recyclerView)
                recyclerView.adapter = subCatRecyclerAdapter
            }

            override fun onFailure(call: Call<List<CourseSubCat>>, t: Throwable) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
            }
        })

    }

}// Required empty public constructor
