package com.mexcelle.whitelable.model

data class LoginResponseData(

    val message: String,
    val status: String,
    val data: ArrayList<LoginResponseDetailData>

)
