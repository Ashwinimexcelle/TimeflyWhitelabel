package com.mexcelle.whitelable.model

data class ActivityDetailsResponseData(

    val status: String,
    val message: String,
    val data: ArrayList<ActivityDetailsResponseDataDetails>,

    )
