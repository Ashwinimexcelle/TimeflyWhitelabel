package com.mexcelle.whitelable.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.databinding.SignupActivityBinding
import com.mexcelle.whitelable.ui.login.LoginActivity
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_password

class RegisterActivity : AppCompatActivity() {


    lateinit var binding: SignupActivityBinding
    lateinit var signupViewModel: SignupViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        signupViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.setLifecycleOwner(this)
        binding.signupViewModel = signupViewModel


        Utility.setFontAwesome(this,password_icon1)
        Utility.setFontAwesome(this,email_icon1)
        Utility.setbold(this,login_txt1)
        Utility.setbold(this,register_txt1)
        Utility.setSemibold(this,button_register)
        Utility.setSemibold(this,register_txt1)
        Utility.setSemibold(this,tv_register_user2)
        Utility.setSemibold(this,tv_login_user1)

        button_register.setOnClickListener {

            if (Utility.isOnline(this@RegisterActivity)) {

                Utility.showProgressDialog(this@RegisterActivity)
                signupViewModel.signup(this@RegisterActivity)!!
                    .observe(this, Observer { signupResponseData ->

                        Utility.showDialog(
                            this,
                            "Register !!",
                            "" + signupResponseData?.message,
                            "Ok",
                            "Cancel"
                        )
                        Utility.hideProgressDialog(this@RegisterActivity)


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

        tv_login_user1.setOnClickListener {

            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)

        }

        password_icon1.setOnClickListener {

            if(password_icon1.text.equals(getString(R.string.icon_hide_password))){

                password_icon1.text = getString(R.string.icon_show_password)
                et_password.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT

            }else{

                password_icon1.text = getString(R.string.icon_hide_password)
                et_password.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD


            }

        }

    }
}