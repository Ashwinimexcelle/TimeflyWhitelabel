package com.mexcelle.whitelable.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mexcelle.whitelable.model.*
import com.mexcelle.whitelable.repository.CharityActivityRepository
import com.mexcelle.whitelable.repository.CharityDetailsActivityRepository
import com.mexcelle.whitelable.repository.LoginActivityRepository

class CharityDetailsViewModel : ViewModel() {

    var activityDetailsResponseData: MutableLiveData<ActivityDetailsResponseData?>? =
        MutableLiveData<ActivityDetailsResponseData?>()

    var joinActivityResponseData: MutableLiveData<JoinActivityResponseData?>? =
        MutableLiveData<JoinActivityResponseData?>()


    var unjoinActivityResponseData: MutableLiveData<UnjoinActivityResponseData?>? =
        MutableLiveData<UnjoinActivityResponseData?>()


    var startEventResponseData: MutableLiveData<StartEventResponseData?>? =
        MutableLiveData<StartEventResponseData?>()


    var stopEventResponseData: MutableLiveData<StopEventResponseData?>? =
        MutableLiveData<StopEventResponseData?>()

    lateinit var joinEventInputData : JoinEventInputData
    lateinit var startEventInputData : StartEventInputData




    fun getCharityDetails(context: Context,charityId: String) : MutableLiveData<ActivityDetailsResponseData?>? {

        activityDetailsResponseData = CharityDetailsActivityRepository.getCharityDetails(context,charityId)

        return activityDetailsResponseData

    }


    fun JoinActivity(context: Context,activityId: String) : MutableLiveData<JoinActivityResponseData?>? {


        joinEventInputData =  JoinEventInputData(activityId)
        joinActivityResponseData = CharityDetailsActivityRepository.joinActivity(context,joinEventInputData)
        return joinActivityResponseData

    }

    fun UnjoinActivity(context: Context) : MutableLiveData<UnjoinActivityResponseData?>? {

        unjoinActivityResponseData = CharityDetailsActivityRepository.unjoinActivity(context)
        return unjoinActivityResponseData

    }

    fun StartEvent(context: Context,activityId: String,eventData: String) : MutableLiveData<StartEventResponseData?>? {

        startEventInputData =  StartEventInputData(eventData)
        startEventResponseData = CharityDetailsActivityRepository.startEvent(context,activityId,startEventInputData)
        return startEventResponseData

    }


    fun StopEvent(context: Context,activityId: String) : MutableLiveData<StopEventResponseData?>? {

        stopEventResponseData = CharityDetailsActivityRepository.stopEvent(context,activityId)
        return stopEventResponseData

    }



}