package com.mexcelle.whitelable.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mexcelle.whitelable.model.CalendarwiseResponseData
import com.mexcelle.whitelable.model.UpcomingActivitiesResponseData
import com.mexcelle.whitelable.retrofit.RetrofitClient
import com.mexcelle.whitelable.util.Constants
import com.mexcelle.whitelable.util.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CharityActivityRepository {

    val upcomingActivitiesResponseData = MutableLiveData<UpcomingActivitiesResponseData?>()

    fun getUpcomingActivities(context: Context): MutableLiveData<UpcomingActivitiesResponseData?> {

        val call = RetrofitClient.apiInterface.getUpcomingActivities(Constants.USER_AUTHTOKEN)
        call.enqueue(object: Callback<UpcomingActivitiesResponseData> {
            override fun onFailure(call: Call<UpcomingActivitiesResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<UpcomingActivitiesResponseData>,
                response: Response<UpcomingActivitiesResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        upcomingActivitiesResponseData.value = data!!

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

        return upcomingActivitiesResponseData
    }


}