package com.mexcelle.whitelable.ui.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.databinding.LoginActivityBinding
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.ui.register.RegisterActivity
import com.mexcelle.whitelable.util.*
import com.mexcelle.whitelable.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var binding: LoginActivityBinding
    lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.setLifecycleOwner(this)
        binding.loginViewModel = loginViewModel
        init();

        button_login.setOnClickListener {


            if (Utility.isOnline(this@LoginActivity)) {

                Utility.showProgressDialog(this@LoginActivity)
                loginViewModel.login(this@LoginActivity)!!
                    .observe(this, Observer { loginResponseData ->


                        if (loginResponseData?.status.equals("success")) {

                            if (loginResponseData?.data?.size!! > 0) {

                                Utility.hideProgressDialog(this@LoginActivity)
                                Constants.USER_NAME = loginResponseData?.data[0].name
                                Constants.USER_EMAILID = loginResponseData?.data[0].email_id
                                Constants.USER_AUTHTOKEN = loginResponseData?.data[0].auth_token
                                Constants.USER_ENTITYNAME = loginResponseData?.data[0].entity_name
                                Constants.USER_COMPANYNAME = loginResponseData?.data[0].company_name

                                prefs.initAuthToken = Constants.USER_AUTHTOKEN
                                prefs.initUsername = Constants.USER_NAME
                                prefs.initEmailid = Constants.USER_EMAILID
                                prefs.initEntityName = Constants.USER_ENTITYNAME
                                prefs.initCompanyName = Constants.USER_COMPANYNAME
                                prefs.initUserImageUrl = Constants.USER_IMAGE_URL
                                prefs.initUserImageUrl = Constants.USER_IMAGE_URL
                                Log.e(
                                    "sharedPref?.initAuthToken",
                                    "initAuthToken " + prefs.initAuthToken
                                );
                                Log.e("initAuthToken", "initAuthToken " + Constants.USER_AUTHTOKEN);

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)

                            } else {

                                Utility.showDialog(
                                    this,
                                    "Error !!",
                                    "" + loginResponseData.message,
                                    "Ok",
                                    "Cancel"
                                )
                                Utility.hideProgressDialog(this@LoginActivity)

                            }

                        } else {

                            Utility.showDialog(
                                this,
                                "Error !!",
                                "" + loginResponseData?.message,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(this@LoginActivity)

                        }


                    })

            } else {

                Utility.showDialog(
                    this,
                    "Network Error !!",
                    "Please check your network connection.",
                    "Ok",
                    "Cancel"
                )

            }


        }

        tv_register_user.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        password_icon.setOnClickListener {

            if (password_icon.text.equals(getString(R.string.icon_hide_password))) {

                password_icon.text = getString(R.string.icon_show_password)
                et_password.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT

            } else {

                password_icon.text = getString(R.string.icon_hide_password)
                et_password.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD


            }

        }

    }


    fun init() {

        Utility.setSemibold(this, welcome_txt_id)
        Utility.setSemibold(this, button_login)
        Utility.setSemibold(this, forgot_password_id)
        Utility.setThin(this, tv_register_user1)
        Utility.setbold(this, tv_register_user)
        Utility.setFontAwesome(this, password_icon)
        Utility.setFontAwesome(this, email_icon)
        Utility.setbold(this, login_txt)


    }


}