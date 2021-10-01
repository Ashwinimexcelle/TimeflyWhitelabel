package com.mexcelle.whitelable.model

data class CalendarwiseResponseData(

    val message: String,
    val status: String,
    val data: ArrayList<CalendarwiseResponseDataDetails>,
)
