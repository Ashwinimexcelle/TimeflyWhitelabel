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
import com.mexcelle.whitelable.R

val prefs: SharedPref by lazy {
    AppController.prefs!!
}

class AppController : Application() {



    companion object {
        var prefs: SharedPref? = null
        lateinit var instance: AppController
            private set
    }

    override fun onCreate() {

        //sharedPref = SharedPref(applicationContext)

        super.onCreate()
        instance = this
        prefs = SharedPref(applicationContext)
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/Roboto-Regular.ttf")
        // CalligraphyConfig.initDefault("fonts/Raleway_Regular.ttf");
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val detectFileUriExposure = builder.detectFileUriExposure()
    }

    }
