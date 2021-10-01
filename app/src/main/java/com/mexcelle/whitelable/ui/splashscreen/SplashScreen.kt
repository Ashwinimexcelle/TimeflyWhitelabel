package com.mexcelle.whitelable.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.login.LoginActivity
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.ui.onboardingscreen.OnBoardingActivity
import com.mexcelle.whitelable.util.AppController
import com.mexcelle.whitelable.util.Constants
import com.mexcelle.whitelable.util.SharedPref
import com.mexcelle.whitelable.util.prefs


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContentView(R.layout.activity_splash_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )




        Log.e("Here","Here ")
        Log.e("Shared","Shared "+prefs?.initAuthToken)

        if(prefs?.initAuthToken != null){

            Log.e("Shared","Shared "+prefs?.initAuthToken)
            if(prefs?.initAuthToken?.equals("1")!!){

                val handler = Handler()
                handler.postDelayed({
                    val intent = Intent(this@SplashScreen, OnBoardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)

            }else{

                Constants.USER_AUTHTOKEN = prefs?.initAuthToken!!
                Constants.USER_EMAILID = prefs?.initEmailid!!
                Constants.USER_NAME = prefs?.initUsername!!
                Constants.USER_COMPANYNAME = prefs?.initCompanyName!!
                Constants.USER_ENTITYNAME = prefs?.initEntityName!!

                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
            }

        }else{


            val handler = Handler()
            handler.postDelayed({
                val intent = Intent(this@SplashScreen, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }


    }
}