package com.mexcelle.whitelable.ui.charity.adapter

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
import com.mexcelle.whitelable.model.UpcomingActivitiesResponseDataDetails
import java.util.ArrayList
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.util.Utility
import okio.Utf8


class CharityAdapter(

    mList: ArrayList<UpcomingActivitiesResponseDataDetails>,
    activityContext: Context, listener: CharityAdapter.OnItemClickListener) : RecyclerView.Adapter<CharityAdapter.MyViewHolder?>(), View.OnClickListener {
    var mContext: Context
    private val listener: OnItemClickListener
    private var image: String? = null
    var upcomingActivitiesResponseDataDetails: ArrayList<UpcomingActivitiesResponseDataDetails> = ArrayList<UpcomingActivitiesResponseDataDetails>()

    init {

        mContext = activityContext
        this.listener = listener
        this.upcomingActivitiesResponseDataDetails = mList
    }


    interface OnItemClickListener {
        fun onItemClick(upcomingActivitiesList: UpcomingActivitiesResponseDataDetails?)
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
                        outline?.setRoundRect(0, 0, view!!.width, (view.height+curveRadius).toInt(), curveRadius)
                    }
                }

                eventImageView.clipToOutline = true

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_raw, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {

        holder.foodTextView.text = Utility.sentenceCaseForText(upcomingActivitiesResponseDataDetails[listPosition].name)
        holder.foodDonationTextView.text = Utility.sentenceCaseForText(upcomingActivitiesResponseDataDetails[listPosition].place)
        Utility.setSemibold(mContext,holder.joinTextview)
        Utility.setRegular(mContext,holder.foodTextView)
        Utility.setSemibold(mContext,holder.foodDonationTextView)

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.placeholder)
        requestOptions.error(R.drawable.placeholder)


        Glide.with(holder.itemView.getContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(upcomingActivitiesResponseDataDetails!!.get(listPosition)!!.image)
            .into(holder.eventImageView);

        holder.eventImageView.setOnClickListener {
            listener.onItemClick(upcomingActivitiesResponseDataDetails[listPosition])
        }


    }

    override fun onClick(view: View) {}



    override fun getItemCount(): Int {
        return upcomingActivitiesResponseDataDetails.size

    }


}
