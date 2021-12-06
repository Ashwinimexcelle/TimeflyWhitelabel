package com.mexcelle.whitelable.util

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import com.mexcelle.whitelable.R
import android.graphics.Typeface
import android.widget.TextView
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.content.pm.PackageManager

import androidx.core.content.ContextCompat
import java.util.concurrent.CopyOnWriteArrayList
import androidx.core.app.ActivityCompat
import android.provider.Settings.SettingNotFoundException

import android.os.Build
import android.provider.Settings
import java.lang.StringBuilder


class Utility private constructor() {
    var data: String? = null


    companion object {

        val instance = Utility()
        var b: AlertDialog? = null
        var dialogBuilder: AlertDialog.Builder? = null
        private var alertDialog: AlertDialog? = null


        fun showDialog(
            context: Context, title: String, msg: String,
            positiveBtnText: String, negativeBtnText: String?
        ) {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialog = alertDialogBuilder.create()
            alertDialogBuilder.setTitle(title)
            alertDialogBuilder.setMessage(msg)
            alertDialogBuilder.setPositiveButton("Ok", { _: DialogInterface, _: Int ->
                alertDialog!!.dismiss()
            })
            alertDialogBuilder.setNegativeButton(
                "Cancel",
                { dialogInterface: DialogInterface, i: Int ->
                    alertDialog!!.dismiss()

                })

            if(alertDialog!!.isShowing){


            }else{

                alertDialogBuilder!!.show()

            }

        }


        fun showProgressDialog(context: Context) {

            dialogBuilder = AlertDialog.Builder(context)
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
            val dialogView: View = inflater!!.inflate(R.layout.loading_indicator, null)
            dialogBuilder!!.setView(dialogView)

            dialogBuilder!!.setCancelable(false)
            b = dialogBuilder!!.create()
            b?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            b!!.show()

        }

        fun hideProgressDialog(context: Context) {
            Log.e("here 2","here 2");

            Log.e("b ","b "+b)
                if(b != null){
                    Log.e("here 3","here 3");

                    b?.dismiss()
                    b?.cancel()
                }else{

                    Log.e("here 4","here 4");

                }

        }

        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

        fun isValidEmail(target: CharSequence?): Boolean {
            return if (TextUtils.isEmpty(target)) {
                false
            } else {
                Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }


        fun setSemibold(context: Context,editText: TextView) {

            val tf = Typeface.createFromAsset(context.getAssets(), "fonts/poppins_semibold.ttf")
            editText.setTypeface(tf)

        }

        fun setbold(context: Context,editText: TextView) {

            val tf = Typeface.createFromAsset(context.getAssets(), "fonts/poppins_bold.ttf")
            editText.setTypeface(tf)

        }

        fun setRegular(context: Context,editText: TextView) {

            val tf = Typeface.createFromAsset(context.getAssets(), "fonts/poppins_medium.ttf")
            editText.setTypeface(tf)

        }

        fun setThin(context: Context,editText: TextView) {

            val tf = Typeface.createFromAsset(context.getAssets(), "fonts/poppins_light.ttf")
            editText.setTypeface(tf)

        }

        fun setFontAwesome(context: Context,editText: TextView) {

            val tf = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesomeregular.otf")
            editText.setTypeface(tf)

        }

        fun setSolidFontAwesome(context: Context,editText: TextView) {

            val tf = Typeface.createFromAsset(context.getAssets(), "fonts/fa-solid-900.ttf")
            editText.setTypeface(tf)

        }

        @Throws(Exception::class)
        fun getparsedDate(date: String): String? {
            val sdf: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)
            var s2: String? = null
            val d: Date
            try {
                d = sdf.parse(date)
                s2 = SimpleDateFormat("yyyy-MM-dd").format(d)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return s2
        }




        fun isDateIsOld(date: String): Boolean? {
            var your_date_is_outdated: Boolean

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val strDate = sdf.parse(date)
            if (Date().after(strDate)) {
                your_date_is_outdated = true
            } else {
                your_date_is_outdated = false
            }
            return your_date_is_outdated
        }


         fun checkLocationPermission(context: Context): Boolean {
            return if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                false
            } else true
        }


        fun isLocationEnabled(context: Context): Boolean {
            var locationMode = 0
            val locationProviders: String
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                locationMode = try {
                    Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
                } catch (e: SettingNotFoundException) {
                    e.printStackTrace()
                    return false
                }
                locationMode != Settings.Secure.LOCATION_MODE_OFF
            } else {
                locationProviders = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED
                )
                !TextUtils.isEmpty(locationProviders)
            }
        }


        fun sentenceCaseForText(text: String?): String? {
            if (text == null) return ""
            var pos = 0
            var capitalize = true
            val sb = StringBuilder(text)
            while (pos < sb.length) {
                if (capitalize && !Character.isWhitespace(sb[pos])) {
                    sb.setCharAt(pos, Character.toUpperCase(sb[pos]))
                } else if (!capitalize && !Character.isWhitespace(sb[pos])) {
                    sb.setCharAt(pos, Character.toLowerCase(sb[pos]))
                }
                capitalize = if (sb[pos] == '.' || capitalize && Character.isWhitespace(sb[pos])) {
                    true
                } else {
                    false
                }
                pos++
            }
            return sb.toString()
        }

    }

}