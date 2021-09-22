package com.mexcelle.whitelable.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
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
            alertDialogBuilder.setPositiveButton("Yes", { _: DialogInterface, _: Int ->
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


    }

}