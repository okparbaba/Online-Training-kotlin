package com.greenhacker.greenhackeronlinetraining.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

import com.greenhacker.greenhackeronlinetraining.models.CoruseMainCat
import com.greenhacker.greenhackeronlinetraining.models.CourseSubCat
import com.greenhacker.greenhackeronlinetraining.models.User


class SharedPrefManager private constructor(context: Context) {


    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null) true else false
        }

    val user: User
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                    sharedPreferences.getInt(KEY_USER_ID, 0),
                    sharedPreferences.getString(KEY_USER_NAME, null),
                    sharedPreferences.getString(KEY_USER_EMAIL, null),
                    sharedPreferences.getString(KEY_USER_GENDER, null)
            )
        }

    init {
        mCtx = context
    }


    fun userLogin(user: User): Boolean {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_USER_ID, user.id)
        editor.putString(KEY_USER_NAME, user.name)
        editor.putString(KEY_USER_EMAIL, user.email)
        editor.putString(KEY_USER_GENDER, user.job)
        editor.apply()
        return true
    }


    fun logout(): Boolean {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        return true
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var mInstance: SharedPrefManager? = null
        @SuppressLint("StaticFieldLeak")
        private lateinit var mCtx: Context

        private val SHARED_PREF_NAME = "simplifiedcodingsharedprefretrofit"

        private val KEY_USER_ID = "keyuserid"
        private val KEY_USER_NAME = "keyusername"
        private val KEY_USER_EMAIL = "keyuseremail"
        private val KEY_USER_GENDER = "keyusergender"
        private val KEY_COURSE = "keyusergender"

        @Synchronized
        fun getInstance(context: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(context)
            }
            return mInstance as SharedPrefManager
        }
    }
}