package com.mexcelle.whitelable.util

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.StrictMode
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.widget.Toast

class AppController : Application() {



    override fun onCreate() {
        super.onCreate()

        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/Roboto-Regular.ttf")
        // CalligraphyConfig.initDefault("fonts/Raleway_Regular.ttf");
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()


        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/ProximaNovaSoft_Medium.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }

    }