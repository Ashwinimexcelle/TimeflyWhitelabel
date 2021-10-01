package com.mexcelle.whitelable.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mexcelle.whitelable.model.CalendarwiseResponseData
import com.mexcelle.whitelable.model.UpcomingActivitiesResponseData
import com.mexcelle.whitelable.repository.CalendarActivityRepository
import com.mexcelle.whitelable.repository.CharityActivityRepository

class CharityViewModel : ViewModel() {

    var upcomingActivitiesResponseData: MutableLiveData<UpcomingActivitiesResponseData?>? =
        MutableLiveData<UpcomingActivitiesResponseData?>()
    fun getUpcomingActivities(context: Context) : MutableLiveData<UpcomingActivitiesResponseData?>? {

        upcomingActivitiesResponseData = CharityActivityRepository.getUpcomingActivities(context)

        return upcomingActivitiesResponseData

    }

}