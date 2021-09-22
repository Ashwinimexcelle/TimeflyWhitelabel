package com.mexcelle.whitelable.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.databinding.LoginActivityBinding
import com.mexcelle.whitelable.ui.register.RegisterActivity
import com.mexcelle.whitelable.util.Utility
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


        button_login.setOnClickListener {

            //wp7progressBar.showProgressBar()
            Utility.showProgressDialog(this@LoginActivity)

            loginViewModel.login(this@LoginActivity)!!.observe(this, Observer { LoginResponseData ->

                Log.e("here 0","here 0");

               /* runOnUiThread(Runnable {*/

                    Log.e("here 1","here 1 ");
                    Utility.hideProgressDialog(this@LoginActivity)


                /*})*/
            })


            /*runOnUiThread(Runnable {

                loginViewModel.login(this@LoginActivity)!!.observe(this, Observer { loginResponseData ->

                    //wp7progressBar.hideProgressBar()
                    Utility.hideProgressDialog(this@LoginActivity)


                })*/



        }

        tv_register_user.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}