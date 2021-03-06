package com.mexcelle.whitelable.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mexcelle.whitelable.model.*
import com.mexcelle.whitelable.repository.LoginActivityRepository
import com.mexcelle.whitelable.repository.ProfileActivityRepository
import com.mexcelle.whitelable.util.Utility
import java.io.File

class ProfileViewModel  : ViewModel() {

    var profileResponseData: MutableLiveData<ProfileResponseData?>? = MutableLiveData<ProfileResponseData?>()
    var profileUpdateResponseData: MutableLiveData<ProfileUpdateResponseData?>? = MutableLiveData<ProfileUpdateResponseData?>()
    var fileUploadResponse: MutableLiveData<FileUploadResponse?>? = MutableLiveData<FileUploadResponse?>()

    var upcomingActivitiesResponseData: MutableLiveData<ProfileUpcomingActivitiesResponseData?>? = MutableLiveData<ProfileUpcomingActivitiesResponseData?>()
    var completedActivitiesResponseData: MutableLiveData<CompletedActivitiesResponseData?>? = MutableLiveData<CompletedActivitiesResponseData?>()
    lateinit var profileInputData : ProfileInputData
    var username : String = ""
    var emailId : String = ""
    var bio : String = ""
    var companyName : String = ""
    var designation : String = ""
    var age : String = ""
    var gender : String = ""
    var ageList: ArrayList<String> = ArrayList()
    var genderList: ArrayList<String> = ArrayList()
    var genderSpinnerSelection: Int = 0
    var ageSpinnerSelection: Int = 0


    /*var age = arrayOf("1", "2", "3", "4")
    var genderList = arrayOf("Male", "Female", "Others")*/

    fun updateSpinnerList(context: Context) {

        genderList.add("Male")
        genderList.add("Female")
        genderList.add("Others")


        for (i in 1..60 step 1){

            ageList.add(i.toString())
        }

        Log.e("genderList ","genderList "+genderList);
        Log.e("ageList ","ageList "+ageList);

    }

    fun getProfile(context: Context) : MutableLiveData<ProfileResponseData?>? {

        profileResponseData = ProfileActivityRepository.getProfile(context)
        return profileResponseData

    }

    fun getUpcomingActivties(context: Context) : MutableLiveData<ProfileUpcomingActivitiesResponseData?>? {

        Log.e("Here 3","Here 3")
        upcomingActivitiesResponseData = ProfileActivityRepository.getUpcomingActivities(context)
        return upcomingActivitiesResponseData

    }

    fun getCompletedActivities(context: Context) : MutableLiveData<CompletedActivitiesResponseData?>? {

        completedActivitiesResponseData = ProfileActivityRepository.getCompletedActivties(context)
        return completedActivitiesResponseData

    }

    fun updateProfile(context: Context) : MutableLiveData<ProfileUpdateResponseData?>? {

        var g:String = ""
        if(gender.equals("Male")){

            g = "M"
        }else if(gender.equals("Female")){

            g = "F"
        }else{

            g = "O"

        }
        profileInputData =  ProfileInputData(username,"",age,bio,g,companyName,designation)
        profileUpdateResponseData = ProfileActivityRepository.updateProfile(context,profileInputData)
        return profileUpdateResponseData

    }

    fun uploadImage(context: Context,userProfileUploadImageRequestModel: UserProfileUploadImageRequestModel) : MutableLiveData<FileUploadResponse?>? {


        fileUploadResponse = ProfileActivityRepository.uploadImage(context,userProfileUploadImageRequestModel)
        return fileUploadResponse

    }

}