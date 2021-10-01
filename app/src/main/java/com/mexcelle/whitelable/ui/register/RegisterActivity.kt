package com.mexcelle.whitelable.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.databinding.LoginActivityBinding
import com.mexcelle.whitelable.databinding.SignupActivityBinding
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.LoginViewModel
import com.mexcelle.whitelable.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {


    lateinit var binding: SignupActivityBinding
    lateinit var signupViewModel: SignupViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        signupViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.setLifecycleOwner(this)
        binding.signupViewModel = signupViewModel


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
    }
}