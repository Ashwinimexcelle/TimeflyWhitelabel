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
import com.mexcelle.whitelable.util.Utility

class LoginViewModel : ViewModel() {

    var loginResponseData: MutableLiveData<LoginResponseData?>? = MutableLiveData<LoginResponseData?>()
    lateinit var loginData : LoginData

    var emailId : String = ""
    var password : String = ""

    fun login(context: Context) : MutableLiveData<LoginResponseData?>? {


        if(Utility.isValidEmail(emailId)){

            loginData =  LoginData(emailId,password)
            loginResponseData = LoginActivityRepository.login(context,loginData)

        }else{

            Utility.hideProgressDialog(context)
            Utility.showDialog(context,"Register","Please enter valid EmailId.","Ok","Cancel")

        }
        return loginResponseData

    }

}