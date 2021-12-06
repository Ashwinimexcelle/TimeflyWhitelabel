package com.mexcelle.whitelable.ui.home.Adapter

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
import com.bumptech.glide.request.RequestOptions
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.model.UpcomingActivitiesResponseDataDetails
import com.mexcelle.whitelable.ui.charity.adapter.CharityAdapter
import com.mexcelle.whitelable.util.Utility
import java.util.*

//UpcomingEventAdapter

class UpcomingEventAdapter(
    mList: ArrayList<UpcomingActivitiesResponseDataDetails>,
    activityContext: Context, listener: UpcomingEventAdapter.OnItemClickListener
) :
    RecyclerView.Adapter<UpcomingEventAdapter.MyViewHolder?>(), View.OnClickListener {
    var mContext: Context
    private val listener: OnItemClickListener
    private var image: String? = null
    var upcomingActivitiesList: ArrayList<UpcomingActivitiesResponseDataDetails> = ArrayList<UpcomingActivitiesResponseDataDetails>()


    init {

        mContext = activityContext
        this.listener = listener
        this.upcomingActivitiesList = mList
    }


    interface OnItemClickListener {
        fun onItemClick(upcomingActivitiesList: UpcomingActivitiesResponseDataDetails?)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var foodDonationTextView: TextView
        var joinTextview: TextView
        var foodTextView: TextView
        var upcomingactivityImageView: ImageView
        val curveRadius = 20F


        init {
            foodDonationTextView = itemView.findViewById<View>(R.id.food_donation_name) as TextView
            foodTextView = itemView.findViewById<View>(R.id.food_name_tv) as TextView
            upcomingactivityImageView = itemView.findViewById<View>(R.id.event_image) as ImageView
            joinTextview = itemView.findViewById<View>(R.id.join_tv) as TextView


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                upcomingactivityImageView.outlineProvider = object : ViewOutlineProvider() {

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

                upcomingactivityImageView.clipToOutline = true

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.upcoming_evets_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {

        holder.foodDonationTextView.text =  Utility.sentenceCaseForText(upcomingActivitiesList[listPosition].name)
        holder.foodTextView.text =  Utility.sentenceCaseForText(upcomingActivitiesList[listPosition].place)


        Utility.setSemibold(mContext,holder.foodTextView)

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.placeholder)
        requestOptions.error(R.drawable.placeholder)


        Glide.with(holder.itemView.getContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(upcomingActivitiesList!!.get(listPosition)!!.image)
            .into(holder.upcomingactivityImageView);

        holder.joinTextview.setOnClickListener {
            listener.onItemClick(upcomingActivitiesList[listPosition])


        }

        holder.upcomingactivityImageView.setOnClickListener {
            listener.onItemClick(upcomingActivitiesList[listPosition])


        }


    }

    override fun onClick(view: View) {}




    override fun getItemCount(): Int {
        return upcomingActivitiesList.size

    }


}

