package com.mexcelle.whitelable.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mexcelle.whitelable.model.CalendarwiseResponseData
import com.mexcelle.whitelable.model.CauseResponseData
import com.mexcelle.whitelable.repository.CalendarActivityRepository

class CalendarViewModel : ViewModel() {

    var calendarwiseResponseData: MutableLiveData<CalendarwiseResponseData?>? =
        MutableLiveData<CalendarwiseResponseData?>()

    var causeResponseData: MutableLiveData<CauseResponseData?>? =
        MutableLiveData<CauseResponseData?>()


    fun getCalendarwiseActivities(context: Context,selectedDate: String,selectedCause: String) : MutableLiveData<CalendarwiseResponseData?>? {

        calendarwiseResponseData = CalendarActivityRepository.getCalendarwiseUpcomingActivities(context,selectedDate,selectedCause)

        return calendarwiseResponseData

    }


    fun getCause(context: Context) : MutableLiveData<CauseResponseData?>? {

        causeResponseData = CalendarActivityRepository.getCause(context)

        return causeResponseData

    }

}