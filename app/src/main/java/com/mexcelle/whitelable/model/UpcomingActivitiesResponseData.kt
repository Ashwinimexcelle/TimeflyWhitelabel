package com.mexcelle.whitelable.model

data class UpcomingActivitiesResponseData(

    val message: String,
    val status: String,
    val data: ArrayList<UpcomingActivitiesResponseDataDetails>,
)
