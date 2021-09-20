package com.mexcelle.whitelable.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.ui.register.RegisterActivity
import com.mexcelle.whitelable.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*binding = DataBindingUtil.setContentView( R.layout.activity_login);
        setContentView(binding.root)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
*/

        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)



      /*  button_login.setOnClickListener {
           *//* val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)*//*

            wp7progressBar.showProgressBar()

            loginViewModel.login()!!.observe(this, Observer { loginResponseData ->

                wp7progressBar.hideProgressBar()

                val msg = loginResponseData.message

            })
        }*/

      /*  tv_register_user.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }*/
    }
}