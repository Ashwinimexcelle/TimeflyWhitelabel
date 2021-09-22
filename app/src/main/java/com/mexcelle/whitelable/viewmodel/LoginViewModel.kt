package com.mexcelle.whitelable.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mexcelle.whitelable.model.LoginData
import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.repository.LoginActivityRepository
import com.mexcelle.whitelable.util.Constants

class LoginViewModel : ViewModel() {

    var loginResponseData: MutableLiveData<LoginResponseData?>? = null
    lateinit var loginData : LoginData
   /* var emailId : MutableLiveData<String> = MutableLiveData<String>()
    var password : MutableLiveData<String> = MutableLiveData<String>()*/

    var emailId : String = ""
    var password : String = ""

    fun login(context: Context) : MutableLiveData<LoginResponseData?>? {

        loginData =  LoginData(emailId,password)
        Log.e("DEBUG : ", loginData.toString())

        loginResponseData = LoginActivityRepository.login(context,loginData)
        return loginResponseData
    }

}