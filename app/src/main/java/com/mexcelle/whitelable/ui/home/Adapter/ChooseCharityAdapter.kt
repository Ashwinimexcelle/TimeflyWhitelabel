package com.mexcelle.whitelable.ui.home.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R

//ChooseCharityAdapter

class ChooseCharityAdapter(activityContext: Context, listener: OnItemClickListener) : RecyclerView.Adapter<ChooseCharityAdapter.MyViewHolder?>(), View.OnClickListener {
    var mContext: Context
    private val listener: OnItemClickListener
    private var image: String? = null

    interface OnItemClickListener {
        fun onItemClick()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var chooseCharityImageView: ImageView

        init {

            chooseCharityImageView = itemView.findViewById<View>(R.id.charity_category_image) as ImageView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.choose_charity, parent, false)
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

