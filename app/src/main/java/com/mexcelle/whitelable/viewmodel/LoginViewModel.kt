package com.mexcelle.whitelable.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.mexcelle.whitelable.model.LoginData
import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.repository.LoginActivityRepository
import com.mexcelle.whitelable.util.Constants

class LoginViewModel : ViewModel() {

    var loginResponseData: MutableLiveData<LoginResponseData>? = null
    lateinit var loginData : LoginData
    var emailId : String? = null
    var password : String? = null

    fun login() : LiveData<LoginResponseData>? {

        //JsonObject postParam = new JsonObject
       // postParam.addProperty("order",yourArray)
        loginData =  LoginData(emailId,password,Constants.entityId)
        Log.e("DEBUG : ", loginData.toString())

        loginResponseData = LoginActivityRepository.login(loginData)
        return loginResponseData
    }

}