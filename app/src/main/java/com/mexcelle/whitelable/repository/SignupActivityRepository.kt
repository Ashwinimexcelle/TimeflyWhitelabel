package com.mexcelle.whitelable.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.model.SignupInputData
import com.mexcelle.whitelable.model.SignupResponseData
import com.mexcelle.whitelable.retrofit.RetrofitClient
import com.mexcelle.whitelable.util.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SignupActivityRepository {

    val signupResponseData = MutableLiveData<SignupResponseData?>()

    fun signUp(context: Context,signupInputData: SignupInputData): MutableLiveData<SignupResponseData?> {

        val call = RetrofitClient.apiInterface.signUp(signupInputData)
        call.enqueue(object: Callback<SignupResponseData> {
            override fun onFailure(call: Call<SignupResponseData>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<SignupResponseData>,
                response: Response<SignupResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        signupResponseData.value = data!!

                    }else{

                        Utility.hideProgressDialog(context)
                        Utility.showDialog( context,"Error !!","Server Error.","Ok","Cancel")
                    }
                }else{

                    Utility.hideProgressDialog(context)
                    Utility.showDialog( context,"Error !!","Server Error.","Ok","Cancel")

                }

            }
        })

        return signupResponseData
    }
}