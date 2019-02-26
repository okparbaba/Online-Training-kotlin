package com.greenhacker.greenhackeronlinetraining.api

import com.greenhacker.greenhackeronlinetraining.models.BlogDetail
import com.greenhacker.greenhackeronlinetraining.models.BlogPosts
import com.greenhacker.greenhackeronlinetraining.models.CompanyProfile
import com.greenhacker.greenhackeronlinetraining.models.CoruseMainCat
import com.greenhacker.greenhackeronlinetraining.models.CourseDetail
import com.greenhacker.greenhackeronlinetraining.models.CourseSubCat
import com.greenhacker.greenhackeronlinetraining.models.Posts
import com.greenhacker.greenhackeronlinetraining.models.Result

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    @get:GET("all_main_categories")
    val mainCat: Call<List<CoruseMainCat>>

    @get:GET("company_profile")
    val companyProfile: Call<CompanyProfile>

    @get:GET("all_blog")
    val blogPost: Call<List<BlogPosts>>


    @get:GET("/photos")
    val postList: Call<List<Posts>>

    @GET("sub_categories/{main_category_id}")
    fun getCourseSubCat(@Path("main_category_id") main_category_id: Int): Call<List<CourseSubCat>>

    @GET("blog_detail/{blog_id}")
    fun getBlogDetail(@Path("blog_id") blog_id: Int): Call<BlogDetail>

    @GET("course_detail/{course_id}")
    fun getCourseDetail(@Path("course_id") course_id: Int): Call<CourseDetail>

    @FormUrlEncoded
    @POST("register")
    fun createUser(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("phone") phone: String,
            @Field("password") password: String,
            @Field("job") job: String,
            @Field("location") location: String,
            @Field("category_id") category_id: String,
            @Field("updated_at") updated_at: String,
            @Field("created_at") created_at: String): Call<Result>


    @FormUrlEncoded
    @POST("login")
    fun userLogin(
            @Field("email") email: String,
            @Field("password") password: String
    ): Call<Result>


}