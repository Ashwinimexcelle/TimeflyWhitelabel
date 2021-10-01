package com.mexcelle.whitelable.util

import android.app.Activity
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


class Utility private constructor() {
    var data: String? = null


    companion object {

        private var alertDialog: AlertDialog? = null
        val instance = Utility()
        var b: AlertDialog? = null
        var dialogBuilder: AlertDialog.Builder? = null


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
            alertDialogBuilder!!.show()

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




    }

}