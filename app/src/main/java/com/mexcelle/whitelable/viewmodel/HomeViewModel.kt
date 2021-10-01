package com.mexcelle.whitelable.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mexcelle.whitelable.model.CauseResponseData
import com.mexcelle.whitelable.model.LoginData
import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.model.UpcomingActivitiesResponseData
import com.mexcelle.whitelable.repository.HomeActivityRepository
import com.mexcelle.whitelable.repository.LoginActivityRepository
import com.mexcelle.whitelable.util.Utility

class HomeViewModel : ViewModel(){

    var causeResponseData: MutableLiveData<CauseResponseData?>? = MutableLiveData<CauseResponseData?>()
    var upcomingActivitiesResponseData: MutableLiveData<UpcomingActivitiesResponseData?>? = MutableLiveData<UpcomingActivitiesResponseData?>()

    fun getCause(context: Context) : MutableLiveData<CauseResponseData?>? {

        causeResponseData = HomeActivityRepository.getCause(context)

        return causeResponseData

    }

    fun getUpcomingActivties(context: Context) : MutableLiveData<UpcomingActivitiesResponseData?>? {

        upcomingActivitiesResponseData = HomeActivityRepository.getUpcomingActivities(context)

        return upcomingActivitiesResponseData

    }


}