package com.mexcelle.whitelable.retrofit


import com.mexcelle.whitelable.model.LoginData
import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.model.SignupResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("Content-Type:application/json")
    @POST("/api/auth/login")
    fun login(@Body loginData:LoginData) : Call<LoginResponseData>


    @Headers("Content-Type:application/json")
    @POST("/api/auth/register")
    fun signUp() : Call<SignupResponseData>
}