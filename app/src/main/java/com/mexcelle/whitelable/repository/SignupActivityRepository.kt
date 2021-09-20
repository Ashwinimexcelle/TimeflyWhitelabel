package com.mexcelle.whitelable.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.model.SignupResponseData
import com.mexcelle.whitelable.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SignupActivityRepository {

    val signupResponseData = MutableLiveData<SignupResponseData>()

    fun signUp(): MutableLiveData<SignupResponseData> {

        val call = RetrofitClient.apiInterface.signUp()
        call.enqueue(object: Callback<SignupResponseData> {
            override fun onFailure(call: Call<SignupResponseData>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<SignupResponseData>,
                response: Response<SignupResponseData>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                val msg = data!!.message

                signupResponseData.value = SignupResponseData(msg)
            }
        })

        return signupResponseData
    }
}