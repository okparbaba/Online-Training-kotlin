package com.greenhacker.greenhackeronlinetraining.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.facebook.shimmer.ShimmerFrameLayout
import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.api.APIService
import com.greenhacker.greenhackeronlinetraining.api.APIUrl
import com.greenhacker.greenhackeronlinetraining.models.CompanyProfile

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass.
 */
class CompanyProfileFragment : Fragment() {
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    internal lateinit var tvCpName: TextView
    internal lateinit var tvCpEmail: TextView
    internal lateinit var tvCpPhone: TextView
    internal lateinit var tvCpAddress: TextView
    internal lateinit var tvCpOurStory: TextView
    internal lateinit var tvCpOurMission: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity!!.title = "About Us "
        return inflater.inflate(R.layout.fragment_company_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container)
        tvCpName = view.findViewById(R.id.cp_name)
        tvCpEmail = view.findViewById(R.id.cp_email)
        tvCpPhone = view.findViewById(R.id.cp_phone)
        tvCpAddress = view.findViewById(R.id.cp_address)
        tvCpOurMission = view.findViewById(R.id.cp_ourMission)
        tvCpOurStory = view.findViewById(R.id.cp_ourStory)
        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create<APIService>(APIService::class.java!!)
        val call = service.companyProfile
        call.enqueue(object : Callback<CompanyProfile> {
            override fun onResponse(call: Call<CompanyProfile>, response: Response<CompanyProfile>) {
                try {
                    val cprName = response.body()!!.name
                    val cprEmail = response.body()!!.email
                    val cprPhone = response.body()!!.phone
                    val cprAddress = response.body()!!.address
                    val cprMission = response.body()!!.our_mission
                    val cprStory = response.body()!!.our_story

                    tvCpName.text = cprName
                    tvCpEmail.text = cprEmail
                    tvCpPhone.text = cprPhone
                    tvCpAddress.text = cprAddress
                    tvCpOurMission.text = cprMission
                    tvCpOurStory.text = cprStory
                    mShimmerViewContainer!!.stopShimmerAnimation()
                    mShimmerViewContainer!!.visibility = View.GONE
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<CompanyProfile>, t: Throwable) {

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
}// Required empty public constructor
