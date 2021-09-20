package com.mexcelle.whitelable.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arindicatorview.ARIndicatorView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.home.Adapter.ChooseCharityAdapter
import com.mexcelle.whitelable.ui.home.Adapter.UpcomingEventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity


class HomeFragment : Fragment() {

    private  var charityButton:Button?=null
    private  var upcomingRecyclerView:RecyclerView?=null
    private  var chooseChairtyRecyclerView:RecyclerView?=null
    private  var chooseChairtyGridView:GridView?=null
    private var arIndicatorView: ARIndicatorView?=null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*
        charityButton!!.setOnClickListener {

            val action =HomeFragmentDirections.actionHomeFragmentToCharityDeatilsFragment("from home fragment")
            navController.navigate(action)



        }*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {
        val  navController=findNavController()

        MainActivity.screenName.text="Home"

        val window = activity!!.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        arIndicatorView=activity!!.findViewById(R.id.ar_indicator)



        chooseChairtyRecyclerView=activity!!.findViewById(R.id.choose_chairty_rv)
        chooseChairtyRecyclerView!!.setOnFlingListener(null);

        // chooseChairtyRecyclerView!!.setNestedScrollingEnabled(false);
        chooseChairtyRecyclerView!!.setLayoutManager(GridLayoutManager(activity, 3))
        chooseChairtyRecyclerView!!.setAdapter(ChooseCharityAdapter(activity!!, object : ChooseCharityAdapter.OnItemClickListener {
            override fun onItemClick() {


            }
        }))

        upcomingRecyclerView=activity!!.findViewById(R.id.upcomming_event_rv)
        upcomingRecyclerView!!.setOnFlingListener(null);

        //   upcomingRecyclerView!!.setNestedScrollingEnabled(false);

        upcomingRecyclerView!!.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false))


        upcomingRecyclerView!!.setAdapter(UpcomingEventAdapter(activity!!, object : UpcomingEventAdapter.OnItemClickListener {
            override fun onItemClick() {

                val action =HomeFragmentDirections.actionHomeFragmentToCharityDeatilsFragment("from home fragment")
                navController.navigate(action)
            }
        }))

        arIndicatorView!!.attachTo(upcomingRecyclerView, true)
        arIndicatorView!!.numberOfIndicators = 5







    }


}