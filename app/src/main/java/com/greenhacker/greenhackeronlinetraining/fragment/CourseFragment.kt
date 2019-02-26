package com.greenhacker.greenhackeronlinetraining.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.facebook.shimmer.ShimmerFrameLayout
import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.adapter.MainCatRecyclerAdapter
import com.greenhacker.greenhackeronlinetraining.api.APIService
import com.greenhacker.greenhackeronlinetraining.api.APIUrl
import com.greenhacker.greenhackeronlinetraining.models.CoruseMainCat
import com.greenhacker.greenhackeronlinetraining.utils.OnBackPressed
import com.greenhacker.greenhackeronlinetraining.utils.RecyclerViewAnimation

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CourseFragment : Fragment(), OnBackPressed {
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var courseMainCatlist: MutableList<CoruseMainCat>
    internal lateinit var mainCatRecyclerAdapter: MainCatRecyclerAdapter
    private var mShimmerViewContainer: ShimmerFrameLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Courses"
        recyclerView = view.findViewById(R.id.rv_main_Cat)
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container)
        courseMainCatlist = ArrayList()
        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<APIService>(APIService::class.java!!)
        val call = service.mainCat
        call.enqueue(object : Callback<List<CoruseMainCat>> {
            override fun onResponse(call: Call<List<CoruseMainCat>>, response: Response<List<CoruseMainCat>>) {

                try {
                    val list = response.body()
                    var coruseMainCat: CoruseMainCat? = null
                    for (i in list!!.indices) {
                        coruseMainCat = CoruseMainCat()
                        val categoryMain = list[i].category_name
                        val categoryId = list[i].id
                        coruseMainCat.category_name = categoryMain
                        coruseMainCat.id = categoryId
                        courseMainCatlist.add(coruseMainCat)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                mainCatRecyclerAdapter = MainCatRecyclerAdapter(courseMainCatlist, activity)
                recyclerView.layoutManager = LinearLayoutManager(activity)
                RecyclerViewAnimation.runLayoutAnimation(recyclerView)
                recyclerView.adapter = mainCatRecyclerAdapter
                mShimmerViewContainer!!.stopShimmerAnimation()
                mShimmerViewContainer!!.visibility = View.GONE

            }

            override fun onFailure(call: Call<List<CoruseMainCat>>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
            }

        })

    }

    override fun onResume() {
        super.onResume()
        mShimmerViewContainer!!.startShimmerAnimation()
    }

    override fun onPause() {
        mShimmerViewContainer!!.stopShimmerAnimation()
        super.onPause()
    }

    override fun onBackPressed() {
        tellFragments()
        activity!!.supportFragmentManager.popBackStack()
    }

    private fun tellFragments() {
        val fragments = fragmentManager!!.fragments
        for (f in fragments) {
            if (f != null && f is CourseFragment)
                f.onBackPressed()
        }
    }

    companion object {
        private val TAG = "Activity"
    }
}
