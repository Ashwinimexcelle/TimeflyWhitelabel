package com.mexcelle.whitelable.retrofit


import com.mexcelle.whitelable.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type:application/json")
    @POST("/api/auth/login")
    fun login(@Body loginData:LoginData) : Call<LoginResponseData>


    @Headers("Content-Type:application/json")
    @POST("/api/auth/register")
    fun signUp(@Body signupInputData:SignupInputData) : Call<SignupResponseData>


    @Headers("Content-Type:application/json")
    @GET("/api/causes")
    fun getCause(@Header("Authorization") String: Any) : Call<CauseResponseData>


    @Headers("Content-Type:application/json")
    @GET("/api/activities/upcoming")
    fun getUpcomingActivities(@Header("Authorization") String: Any) : Call<UpcomingActivitiesResponseData>

    @Headers("Content-Type:application/json")
    @GET("/api/user/activities/upcoming")
    fun getProfileUpcomingActivities(@Header("Authorization") String: Any) : Call<UpcomingActivitiesResponseData>



    @Headers("Content-Type:application/json")
    @POST("/api/user/view-profile")
    fun getProfile(@Header("Authorization") String: Any) : Call<ProfileResponseData>


    @Headers("Content-Type:application/json")
    @PUT("/api/user/view-profile")
    fun updateProfile(@Header("Authorization") String: Any,@Body profileInputData:ProfileInputData) : Call<ProfileUpdateResponseData>


    @Headers("Content-Type:application/json")
    @GET("/api/user/activities/past")
    fun getCompletedActivities(@Header("Authorization") String: Any) : Call<CompletedActivitiesResponseData>


   /* @Headers("Content-Type:application/json")
    @GET("/api/activities/upcoming")
    fun getCompletedActivities(@Header("Authorization") String: Any) : Call<UpcomingActivitiesResponseData>
*/


    @Headers("Content-Type:application/json")
    @GET("/api/user/view-profile")
    fun getCalendarwiseUpcomingActivities(@Header("Authorization") String: Any) : Call<CalendarwiseResponseData>






}