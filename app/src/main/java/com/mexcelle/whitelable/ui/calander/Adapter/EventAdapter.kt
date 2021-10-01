package com.mexcelle.whitelable.ui.calander.Adapter

import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.model.CalendarwiseResponseData
import com.mexcelle.whitelable.model.CalendarwiseResponseDataDetails
import com.mexcelle.whitelable.model.UpcomingActivitiesResponseDataDetails
import java.util.ArrayList


class EventAdapter(
    mList: ArrayList<CalendarwiseResponseDataDetails>,
    activityContext: Context, listener: OnItemClickListener
) : RecyclerView.Adapter<EventAdapter.MyViewHolder?>(), View.OnClickListener {
    var mContext: Context
    var calendarwiseUpcomingActivitiesList: ArrayList<CalendarwiseResponseDataDetails> = ArrayList<CalendarwiseResponseDataDetails>()

    private val listener: OnItemClickListener
    private var image: String? = null

    interface OnItemClickListener {
        fun onItemClick()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var foodDonationTextView: TextView
        var joinTextview: TextView
        var foodTextView: TextView
        var eventImageView: ImageView
        val curveRadius = 20F


        init {
            foodDonationTextView = itemView.findViewById<View>(R.id.food_donation_name) as TextView
            foodTextView = itemView.findViewById<View>(R.id.food_name_tv) as TextView
            eventImageView = itemView.findViewById<View>(R.id.event_image) as ImageView
            joinTextview = itemView.findViewById<View>(R.id.join_tv) as TextView



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                eventImageView.outlineProvider = object : ViewOutlineProvider() {

                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun getOutline(view: View?, outline: Outline?) {
                        outline?.setRoundRect(
                            0,
                            0,
                            view!!.width,
                            (view.height + curveRadius).toInt(),
                            curveRadius
                        )
                    }
                }

                eventImageView.clipToOutline = true

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_raw, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {

        holder.foodTextView.text = calendarwiseUpcomingActivitiesList[listPosition].name
        holder.foodDonationTextView.text = calendarwiseUpcomingActivitiesList[listPosition].place
        Glide.with(holder.itemView.getContext()).load(calendarwiseUpcomingActivitiesList!!.get(listPosition)!!.image)
            .into(holder.eventImageView);
        holder.eventImageView.setOnClickListener {
            listener.onItemClick()
        }

    }

    override fun onClick(view: View) {

    }


    init {

        mContext = activityContext
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return calendarwiseUpcomingActivitiesList.size

    }


}
