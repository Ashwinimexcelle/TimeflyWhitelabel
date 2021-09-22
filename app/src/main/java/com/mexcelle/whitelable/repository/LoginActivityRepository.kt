package com.mexcelle.whitelable.repository

import android.app.Activity
import android.content.Context
import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.retrofit.RetrofitClient
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mexcelle.whitelable.model.LoginData
import com.mexcelle.whitelable.util.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginActivityRepository {

    val loginResponseData = MutableLiveData<LoginResponseData?>()

    fun login(context: Context, loginData: LoginData): MutableLiveData<LoginResponseData?> {
        Log.e("DEBUG : ", loginData.toString())

        val call = RetrofitClient.apiInterface.login(loginData)
        call.enqueue(object: Callback<LoginResponseData> {
            override fun onFailure(call: Call<LoginResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<LoginResponseData>,
                response: Response<LoginResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        val msg = data!!.message

                        loginResponseData.value = data


                    }else{

                        Utility.showDialog( context,"Error !!","Server Error.","Ok","Cancel")
                    }
                }else{

                    Utility.showDialog( context,"Error !!","Server Error.","Ok","Cancel")

                }

            }
        })

        return loginResponseData
    }
}