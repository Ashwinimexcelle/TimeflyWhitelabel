package com.mexcelle.whitelable.retrofit


import com.mexcelle.whitelable.model.LoginData
import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.model.SignupResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @POST("/auth/register")
    fun login(@Body loginData:LoginData) : Call<LoginResponseData>


    @POST("/auth/login")
    fun signUp() : Call<SignupResponseData>
}