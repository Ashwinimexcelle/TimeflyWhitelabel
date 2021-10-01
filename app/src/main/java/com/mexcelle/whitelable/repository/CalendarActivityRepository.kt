package com.mexcelle.whitelable.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mexcelle.whitelable.model.CalendarwiseResponseData
import com.mexcelle.whitelable.retrofit.RetrofitClient
import com.mexcelle.whitelable.util.Constants
import com.mexcelle.whitelable.util.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CalendarActivityRepository {

    val calendarwiseResponseData = MutableLiveData<CalendarwiseResponseData?>()

    fun getCalendarwiseUpcomingActivities(context: Context): MutableLiveData<CalendarwiseResponseData?> {

        val call = RetrofitClient.apiInterface.getCalendarwiseUpcomingActivities(Constants.USER_AUTHTOKEN)
        call.enqueue(object: Callback<CalendarwiseResponseData> {
            override fun onFailure(call: Call<CalendarwiseResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<CalendarwiseResponseData>,
                response: Response<CalendarwiseResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        calendarwiseResponseData.value = data!!

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

        return calendarwiseResponseData
    }


}