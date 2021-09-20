package com.mexcelle.whitelable.repository

import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.retrofit.RetrofitClient
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mexcelle.whitelable.model.LoginData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginActivityRepository {

    val loginResponseData = MutableLiveData<LoginResponseData>()

    fun login(loginData: LoginData): MutableLiveData<LoginResponseData> {
        Log.e("DEBUG : ", loginData.toString())

        val call = RetrofitClient.apiInterface.login(loginData)
        call.enqueue(object: Callback<LoginResponseData> {
            override fun onFailure(call: Call<LoginResponseData>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<LoginResponseData>,
                response: Response<LoginResponseData>
            ) {
                // TODO("Not yet implemented")
                Log.e("DEBUG : ", response.body().toString())
                val data = response.body()
                val msg = data!!.message
                loginResponseData.value = LoginResponseData(msg)
            }
        })

        return loginResponseData
    }
}