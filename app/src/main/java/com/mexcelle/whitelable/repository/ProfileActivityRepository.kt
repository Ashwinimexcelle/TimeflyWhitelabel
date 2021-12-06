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

object ProfileActivityRepository {

    val profileResponseData = MutableLiveData<ProfileResponseData?>()
    val upcomingActivitiesResponseData = MutableLiveData<ProfileUpcomingActivitiesResponseData?>()
    val completedActivitiesResponseData = MutableLiveData<CompletedActivitiesResponseData?>()
    val profileUpdateResponseData = MutableLiveData<ProfileUpdateResponseData?>()
    val fileUploadResponse = MutableLiveData<FileUploadResponse?>()



    fun getProfile(context: Context): MutableLiveData<ProfileResponseData?> {


        val call = RetrofitClient.apiInterface.getProfile(Constants.USER_AUTHTOKEN)
        call.enqueue(object: Callback<ProfileResponseData> {
            override fun onFailure(call: Call<ProfileResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<ProfileResponseData>,
                response: Response<ProfileResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        profileResponseData.value = data!!

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

        return profileResponseData
    }


    fun getUpcomingActivities(context: Context): MutableLiveData<ProfileUpcomingActivitiesResponseData?> {
        Log.e("Here 4","Here 4")

        val call = RetrofitClient.apiInterface.getProfileUpcomingActivities(Constants.USER_AUTHTOKEN)
        call.enqueue(object: Callback<ProfileUpcomingActivitiesResponseData> {
            override fun onFailure(call: Call<ProfileUpcomingActivitiesResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
                Log.e("Here 5","Here 5")

            }

            override fun onResponse(
                call: Call<ProfileUpcomingActivitiesResponseData>,
                response: Response<ProfileUpcomingActivitiesResponseData>
            ) {
                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        upcomingActivitiesResponseData.value = data!!

                    }else{

                        Log.e("Here 5","Here 5")

                        Utility.hideProgressDialog(context)
                        Utility.showDialog( context,"Error !!","Server Error.","Ok","Cancel")
                    }
                }else{

                    Log.e("Here 6","Here 6")

                    Utility.hideProgressDialog(context)
                    Utility.showDialog( context,"Error !!","Server Error.","Ok","Cancel")

                }

            }
        })

        return upcomingActivitiesResponseData
    }

    fun getCompletedActivties(context: Context): MutableLiveData<CompletedActivitiesResponseData?> {

        val call = RetrofitClient.apiInterface.getCompletedActivities(Constants.USER_AUTHTOKEN)
        call.enqueue(object: Callback<CompletedActivitiesResponseData> {
            override fun onFailure(call: Call<CompletedActivitiesResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<CompletedActivitiesResponseData>,
                response: Response<CompletedActivitiesResponseData>
            ) {

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        completedActivitiesResponseData.value = data!!

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

        return completedActivitiesResponseData
    }




    fun updateProfile(context: Context,profileInputData: ProfileInputData): MutableLiveData<ProfileUpdateResponseData?> {

        val call = RetrofitClient.apiInterface.updateProfile(Constants.USER_AUTHTOKEN,profileInputData)
        call.enqueue(object: Callback<ProfileUpdateResponseData> {
            override fun onFailure(call: Call<ProfileUpdateResponseData>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<ProfileUpdateResponseData>,
                response: Response<ProfileUpdateResponseData>
            ) {

                Log.e("DEBUG1 : ",response.body().toString() )

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        profileUpdateResponseData.value = data!!

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

        return profileUpdateResponseData
    }


    fun uploadImage(context: Context,userProfileUploadImageRequestModel: UserProfileUploadImageRequestModel): MutableLiveData<FileUploadResponse?> {

        val call = RetrofitClient.apiInterface.uploadImage(Constants.USER_AUTHTOKEN,userProfileUploadImageRequestModel.file)
        call?.enqueue(object: Callback<FileUploadResponse> {
            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {

                Log.e("DEBUG1 : ",response.body().toString() )

                if(response.body()!= null){

                    if(response.body().toString() !=null){

                        val data = response.body()
                        fileUploadResponse.value = data!!

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

        return fileUploadResponse
    }




}