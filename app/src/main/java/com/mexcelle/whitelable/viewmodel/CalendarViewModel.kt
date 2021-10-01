package com.mexcelle.whitelable.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mexcelle.whitelable.model.CalendarwiseResponseData
import com.mexcelle.whitelable.repository.CalendarActivityRepository

class CalendarViewModel : ViewModel() {

    var calendarwiseResponseData: MutableLiveData<CalendarwiseResponseData?>? =
        MutableLiveData<CalendarwiseResponseData?>()
    fun getCalendarwiseActivities(context: Context) : MutableLiveData<CalendarwiseResponseData?>? {

        calendarwiseResponseData = CalendarActivityRepository.getCalendarwiseUpcomingActivities(context)

        return calendarwiseResponseData

    }

}