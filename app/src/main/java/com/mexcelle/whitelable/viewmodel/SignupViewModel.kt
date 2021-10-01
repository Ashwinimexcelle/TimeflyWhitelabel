package com.mexcelle.whitelable.viewmodel

import android.content.Context
import android.provider.SyncStateContract
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mexcelle.whitelable.model.LoginData
import com.mexcelle.whitelable.model.LoginResponseData
import com.mexcelle.whitelable.model.SignupInputData
import com.mexcelle.whitelable.model.SignupResponseData
import com.mexcelle.whitelable.repository.LoginActivityRepository
import com.mexcelle.whitelable.repository.SignupActivityRepository
import com.mexcelle.whitelable.util.Constants
import com.mexcelle.whitelable.util.Utility
import android.text.TextUtils
import android.util.Patterns


class SignupViewModel  : ViewModel() {

    var signupResponseData: MutableLiveData<SignupResponseData?>? = MutableLiveData<SignupResponseData?>()
    lateinit var signupInputData: SignupInputData

    var emailId: String = ""
    var password: String = ""
    var confirmpassword: String = ""


    fun signup(context: Context): MutableLiveData<SignupResponseData?>? {

        if(Utility.isValidEmail(emailId)){

            if(!password.equals(confirmpassword)){

                Utility.hideProgressDialog(context)
                Utility.showDialog(context,"Register","Password and confirm password must be same.","Ok","Cancel")

            }else{

                signupInputData = SignupInputData(emailId, password, Constants.entityId)
                Log.e("DEBUG : ", signupInputData.toString())
                signupResponseData = SignupActivityRepository.signUp(context, signupInputData)
            }

        }else{

            Utility.hideProgressDialog(context)
            Utility.showDialog(context,"Register","Please enter valid EmailId.","Ok","Cancel")

        }

        return signupResponseData

    }

}
