package com.mexcelle.whitelable.ui.home.Adapter

import android.content.Context
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.model.CauseResponseData
import com.mexcelle.whitelable.model.CauseResponseDataDetails
import com.mexcelle.whitelable.util.Utility
import java.util.Collections.addAll

//ChooseCharityAdapter

class CauseAdapterAdapter(
    mList: ArrayList<CauseResponseDataDetails>,
    activityContext: Context,
    listener: CauseAdapterAdapter.OnItemClickListener
) : RecyclerView.Adapter<CauseAdapterAdapter.MyViewHolder?>(), View.OnClickListener {

    var mContext: Context
    private val listener: OnItemClickListener
    private var image: String? = null
    var causeList: ArrayList<CauseResponseDataDetails> = ArrayList<CauseResponseDataDetails>()


    init {

        mContext = activityContext
        this.listener = listener
        causeList = mList
        Log.e("here in adapter11","here in adapter11");
        notifyDataSetChanged()

    }

    fun setDataChange(asList: ArrayList<CauseResponseDataDetails>) {
        //now, tell the adapter about the update
        Log.e("here in update","here in update");
        this.causeList.clear();
        this.causeList = asList
        addAll(asList);
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var causeImageView: ImageView
        var causeNameTextView: TextView


        init {
            Log.e("here in adapter1122","here in adapter1122");

            causeImageView = itemView.findViewById<View>(R.id.charity_category_image) as ImageView
            causeNameTextView = itemView.findViewById<View>(R.id.cause_name) as TextView


        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.choose_charity, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {


        Log.e("here in adapter 33333","here in adapter 33333");
        holder.causeNameTextView.text = causeList!!.get(listPosition)!!.name
        Glide.with(holder.itemView.getContext()).load(causeList!!.get(listPosition)!!.image)
            .into(holder.causeImageView);

        Utility.setRegular(mContext,holder.causeNameTextView)


        holder.causeNameTextView.setOnClickListener {
            listener.onItemClick()


        }

        holder.causeImageView.setOnClickListener {
            listener.onItemClick()


        }




    }

    override fun onClick(view: View) {


    }



    override fun getItemCount(): Int {

        return causeList!!.size

    }


}

