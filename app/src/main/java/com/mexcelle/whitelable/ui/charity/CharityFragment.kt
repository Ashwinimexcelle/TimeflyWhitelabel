package com.mexcelle.whitelable.ui.charity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.calander.Adapter.EventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.skyhope.eventcalenderlibrary.model.Event
import java.util.*

class CharityFragment : Fragment() {
    var charityRecyclerview: RecyclerView?=null
    private lateinit var adapter: EventAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_charity, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        Log.e("method call","onActivityCreated")
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {
        MainActivity.screenName.text="Charity"
        val  navController=findNavController()


        charityRecyclerview = activity!!.findViewById(R.id.charirty_recylerview)
        charityRecyclerview!!.setOnFlingListener(null);

        //   upcomingRecyclerView!!.setNestedScrollingEnabled(false);

        charityRecyclerview!!.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))


        charityRecyclerview!!.setAdapter(EventAdapter(activity!!, object : EventAdapter.OnItemClickListener {
            override fun onItemClick() {
                val action =CharityFragmentDirections.actionCharityFragmentToCharityDeatilsFragment("from charity fragment")
                navController.navigate(action)



            }
        }))

    }

}