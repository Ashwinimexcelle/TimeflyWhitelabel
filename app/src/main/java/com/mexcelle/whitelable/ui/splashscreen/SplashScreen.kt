package com.mexcelle.whitelable.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.login.LoginActivity
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.ui.onboardingscreen.OnBoardingActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreen, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}