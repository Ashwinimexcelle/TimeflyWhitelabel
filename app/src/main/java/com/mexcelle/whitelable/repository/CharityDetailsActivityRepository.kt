package com.mexcelle.whitelable.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mexcelle.whitelable.model.*
import com.mexcelle.whitelable.retrofit.RetrofitClient
import com.mexcelle.whitelable.util.Constants
import com.mexcelle.whitelable.util.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CharityDetailsActivityRepository  {

    val activityDetailsResponseData = MutableLiveData<ActivityDetailsResponseData?>()
    val joinActivityResponseData = MutableLiveData<JoinActivityResponseData?>()
    val unjoinActivityResponseData = MutableLiveData<UnjoinActivityResponseData?>()
    val startEventResponseData = MutableLiveData<StartEventResponseData?>()
    val stopEventResponseData = MutableLiveData<StopEventResponseData?>()




    fun getCharityDetails(context: Context,charityId: String): MutableLiveData<ActivityDetailsResponseData?> {

        val call = RetrofitClient.apiInterface.getCharityDetails(Constants.USER_AUTHTOKEN,Constants.entityId,charityId)
        call.enqueue(object: Callback<ActivityDetailsResponseData> {
            override fun onFailure(call: Call<ActivityDetailsResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }
            override fun onResponse(
                call: Call<ActivityDetailsResponseData>,
                response: Response<ActivityDetailsResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        activityDetailsResponseData.value = data!!

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

        return activityDetailsResponseData
    }



    fun joinActivity(context: Context,joinEventInputData: JoinEventInputData): MutableLiveData<JoinActivityResponseData?> {

        val call = RetrofitClient.apiInterface.joinActivity(Constants.USER_AUTHTOKEN,joinEventInputData)
        call.enqueue(object: Callback<JoinActivityResponseData> {
            override fun onFailure(call: Call<JoinActivityResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<JoinActivityResponseData>,
                response: Response<JoinActivityResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        joinActivityResponseData.value = data!!

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

        return joinActivityResponseData
    }



    fun unjoinActivity(context: Context): MutableLiveData<UnjoinActivityResponseData?> {

        val call = RetrofitClient.apiInterface.unjoinActivity(Constants.USER_AUTHTOKEN)
        call.enqueue(object: Callback<UnjoinActivityResponseData> {
            override fun onFailure(call: Call<UnjoinActivityResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<UnjoinActivityResponseData>,
                response: Response<UnjoinActivityResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        unjoinActivityResponseData.value = data!!

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

        return unjoinActivityResponseData
    }



    fun startEvent(context: Context,activityId: String,startEventInputData:StartEventInputData): MutableLiveData<StartEventResponseData?> {

        val call = RetrofitClient.apiInterface.startEvent(Constants.USER_AUTHTOKEN,activityId,startEventInputData)
        call.enqueue(object: Callback<StartEventResponseData> {
            override fun onFailure(call: Call<StartEventResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<StartEventResponseData>,
                response: Response<StartEventResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        startEventResponseData.value = data!!

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

        return startEventResponseData
    }


    fun stopEvent(context: Context,activityId: String): MutableLiveData<StopEventResponseData?> {

        val call = RetrofitClient.apiInterface.stopEvent(Constants.USER_AUTHTOKEN,activityId)
        call.enqueue(object: Callback<StopEventResponseData> {
            override fun onFailure(call: Call<StopEventResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<StopEventResponseData>,
                response: Response<StopEventResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        stopEventResponseData.value = data!!

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

        return stopEventResponseData
    }

}