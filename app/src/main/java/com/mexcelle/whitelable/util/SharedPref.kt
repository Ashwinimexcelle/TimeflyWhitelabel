package com.mexcelle.whitelable.util
import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private val PREFS_FILENAME = "com.mexcelle.whitelable"
    private val USER_NAME = "username"
    private val USER_EMAILID = "emailid"
    private val USER_AUTHTOKEN = "authtoken"
    private val USER_ENTITYNAME = "entityname"
    private val USER_COMPANYNAME = "companyname"

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
   // private val preferences: SharedPreferences = context.getSharedPreferences(Context.MODE_PRIVATE)

    var initUsername: String?
        get() = preferences.getString(USER_NAME, "1")
        set(value) = preferences.edit().putString(USER_NAME, value).apply()

    var initEmailid: String?
        get() = preferences.getString(USER_EMAILID, "1")
        set(value) = preferences.edit().putString(USER_EMAILID, value).apply()


    var initAuthToken: String?
        get() = preferences.getString(USER_AUTHTOKEN, "1")
        set(value) = preferences.edit().putString(USER_AUTHTOKEN, value).apply()

    var initEntityName: String?
        get() = preferences.getString(USER_ENTITYNAME, "1")
        set(value) = preferences.edit().putString(USER_ENTITYNAME, value).apply()

    var initCompanyName: String?
        get() = preferences.getString(USER_COMPANYNAME, "1")
        set(value) = preferences.edit().putString(USER_COMPANYNAME, value).apply()
}