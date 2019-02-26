package com.greenhacker.greenhackeronlinetraining.activitys

import android.app.ProgressDialog
import android.content.Intent
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.api.APIService
import com.greenhacker.greenhackeronlinetraining.api.APIUrl
import com.greenhacker.greenhackeronlinetraining.models.Result
import com.greenhacker.greenhackeronlinetraining.models.User
import com.greenhacker.greenhackeronlinetraining.utils.MyBounceInterpolator
import com.greenhacker.greenhackeronlinetraining.utils.SharedPrefManager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    internal lateinit var btSignUp: Button
    private lateinit var btSpCancel: Button
    internal lateinit var spinnerRegion: Spinner
    internal lateinit var spinnerLanguage: Spinner
    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etJobDes: TextInputEditText
    private lateinit var etPassword: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        etName = findViewById(R.id.etrgName)
        etEmail = findViewById(R.id.etrgEmail)
        etPhone = findViewById(R.id.etetrgPhone)
        etJobDes = findViewById(R.id.etrgJob)
        etPassword = findViewById(R.id.etrgPassword)
        spinnerRegion = findViewById(R.id.spinnerRegion)
        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        btSignUp = findViewById(R.id.btSignUp)
        btSpCancel = findViewById(R.id.btSCancel)

        btSignUp.setOnClickListener(this)
        btSpCancel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btSignUp -> {
                if (v === btSignUp) {
                    registerProcess()
                }
                didTapButton(btSignUp)
            }
            R.id.btSCancel -> {
                didTapButton(btSpCancel)
                finish()
            }
        }
    }

    private fun registerProcess() {

        val rgName = etName.text!!.toString()
        if (TextUtils.isEmpty(rgName)) {
            etName.error = "Can't Blank Name"
            return
        }
        val rgEmail = etEmail.text!!.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(rgEmail)) {
            etEmail.error = "Can't Blank Email"
            return
        }
        val rgPhone = etPhone.text!!.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(rgPhone)) {
            etPhone.error = "Can't Blank Phone"
            return
        }
        val rgJobDes = etJobDes.text!!.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(rgJobDes)) {
            etJobDes.error = "Can't Blank Description"
            return
        }
        val rgPassword = etPassword.text!!.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(rgPassword) || rgPassword.length < 6) {
            etPassword.error = "Password can't blank and ...."
            return
        }
        val spRegion = spinnerRegion.selectedItem.toString()
        val spLanguage = spinnerLanguage.selectedItemId.toInt()
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Signing Up...")
        progressDialog.show()


        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<APIService>(APIService::class.java!!)
        val user = User(rgName, rgEmail, rgPhone, rgPassword, rgJobDes, spRegion, spLanguage.toString() + "", "12", "12")
        val call = service.createUser(
                user.name!!,
                user.email!!,
                user.phone,
                user.password!!,
                user.job,
                user.location,
                user.category_id,
                user.updated_at,
                user.created_at)

        call.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                progressDialog.dismiss()
                if (response.body()!!.result == "success") {
                    //starting profile activity
                    finish()
                    SharedPrefManager.getInstance(applicationContext).userLogin(User(0, null, rgEmail, rgPassword))
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "Complete the fields", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })

    }

    fun didTapButton(view: View) {
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator

        view.startAnimation(myAnim)
    }
}
