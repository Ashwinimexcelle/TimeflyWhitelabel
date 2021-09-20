package com.mexcelle.whitelable.ui.profle.Adapter

import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R



class ProfileUpcommingEventsAdapter(activityContext: Context, listener: OnItemClickListener) : RecyclerView.Adapter<ProfileUpcommingEventsAdapter.MyViewHolder?>(), View.OnClickListener {
    var mContext: Context
    private val listener: OnItemClickListener
    private var image: String? = null


    interface OnItemClickListener {
        fun onItemClick()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var chooseCharityImageView: ImageView
        val curveRadius = 20F


        init {

            chooseCharityImageView = itemView.findViewById<View>(R.id.profile_event_image) as ImageView


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                chooseCharityImageView.outlineProvider = object : ViewOutlineProvider() {

                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun getOutline(view: View?, outline: Outline?) {
                        outline?.setRoundRect(0, 0, view!!.width, (view.height+curveRadius).toInt(), curveRadius)
                    }
                }

                chooseCharityImageView.clipToOutline = true

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.profile_upcomming_event, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {

    }

    override fun onClick(view: View) {}


    init {

        mContext = activityContext
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return 9

    }


}

