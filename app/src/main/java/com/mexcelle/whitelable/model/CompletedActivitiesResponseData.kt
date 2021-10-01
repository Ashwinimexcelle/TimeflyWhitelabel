package com.mexcelle.whitelable.model

data class CompletedActivitiesResponseData(
    val message: String,
    val status: String,
    val data: ArrayList<CompletedActivitiesResponseDataDetails>,

)
