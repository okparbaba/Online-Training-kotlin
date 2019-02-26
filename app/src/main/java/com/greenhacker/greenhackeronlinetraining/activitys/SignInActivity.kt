package com.greenhacker.greenhackeronlinetraining.activitys

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnLogin: Button
    private lateinit var btnCancel: Button
    private lateinit var tvAccReg: TextView
    private lateinit var tvForgetPass: TextView
    private lateinit var etlEmail: EditText
    private lateinit var etlPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        tvAccReg = findViewById(R.id.tvnoAcc)
        tvForgetPass = findViewById(R.id.tvforgetPassword)
        btnLogin = findViewById(R.id.btLogin)
        btnCancel = findViewById(R.id.btCancel)

        tvAccReg.setOnClickListener(this)
        tvForgetPass.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        btnCancel.setOnClickListener(this)

        etlEmail = findViewById(R.id.etloginEmail)
        etlPass = findViewById(R.id.etloginPassword)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btLogin -> {
                Login()
                didTapButton(btnLogin)
            }
            R.id.btCancel -> {
                didTapButton(btnCancel)
                finish()
            }
            R.id.tvforgetPassword -> didTapButton(tvForgetPass)
            R.id.tvnoAcc -> {
                didTapButton(tvAccReg)
                val i = Intent(this@SignInActivity, SignUpActivity::class.java)
                startActivity(i)
            }
        }
    }

    private fun Login() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Signing In...")
        progressDialog.show()

        val email = etlEmail.text.toString().trim { it <= ' ' }
        val password = etlPass.text.toString().trim { it <= ' ' }

        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<APIService>(APIService::class.java!!)

        val call = service.userLogin(email, password)
        call.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                progressDialog.dismiss()
                if (response.body()!!.result == "success") {
                    finish()
                    SharedPrefManager.getInstance(applicationContext).userLogin(User(0, null, email, password))
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Invalid email or password", Toast.LENGTH_LONG).show()
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
