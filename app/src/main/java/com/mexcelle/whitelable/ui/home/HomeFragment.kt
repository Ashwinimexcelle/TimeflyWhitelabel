package com.mexcelle.whitelable.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arindicatorview.ARIndicatorView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.home.Adapter.CauseAdapterAdapter
import com.mexcelle.whitelable.ui.home.Adapter.UpcomingEventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var navController: NavController
    private var charityButton: Button? = null
    private var upcomingRecyclerView: RecyclerView? = null
    private var causeRecyclerView: RecyclerView? = null
    private var chooseChairtyGridView: GridView? = null
    private var arIndicatorView: ARIndicatorView? = null
    lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {

        navController = findNavController()
        //activity?.getActionBar()?.setDisplayShowHomeEnabled(false)
        //activity?.getActionBar()?.setDisplayHomeAsUpEnabled(false)
        MainActivity.screenName.text = "Home"

        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        arIndicatorView = requireActivity().findViewById(R.id.ar_indicator)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(isAdded){

            Log.e("","here in is added");
            init()
            getCause()
        }else{

            Log.e("","here in is not added");

        }

    }

    private fun getCause() {

        if (Utility.isOnline(requireActivity())) {

            Utility.showProgressDialog(requireActivity())
            homeViewModel.getCause(requireActivity())!!
                .observe(requireActivity(), Observer { causeResponseData ->

                    if (causeResponseData?.data?.size!! > 0) {

                        Utility.hideProgressDialog(requireActivity())
                        causeRecyclerView = requireActivity().findViewById(R.id.cause_rv)
                        causeRecyclerView!!.setOnFlingListener(null);
                        causeRecyclerView!!.setLayoutManager(GridLayoutManager(activity, 3))
                        causeRecyclerView!!.setAdapter(
                            CauseAdapterAdapter(
                                causeResponseData.data,
                                requireActivity(),
                                object : CauseAdapterAdapter.OnItemClickListener {
                                    override fun onItemClick() {


                                    }
                                })
                        )

                        getUpcomingActivities()

                    } else {

                        Utility.showDialog(
                            requireContext(),
                            "Error !!",
                            "" + causeResponseData.message,
                            "Ok",
                            "Cancel"
                        )
                        Utility.hideProgressDialog(requireContext())

                    }
                })

        } else {

            Utility.showDialog(
                requireContext(),
                "Network Error !!",
                "Please check your network connection.",
                "Ok",
                "Cancel"
            )
        }
    }

    private fun getUpcomingActivities() {

        if (Utility.isOnline(requireActivity())) {

            Utility.showProgressDialog(requireActivity())
            homeViewModel.getUpcomingActivties(requireActivity())!!
                .observe(requireActivity(), Observer { upcomingActivitiesResponseData ->

                    if (upcomingActivitiesResponseData?.data?.size!! > 0) {

                        Utility.hideProgressDialog(requireActivity())

                        upcomingRecyclerView = requireActivity().findViewById(R.id.upcomming_activities_rv)
                        upcomingRecyclerView!!.setOnFlingListener(null);
                        upcomingRecyclerView!!.setLayoutManager(
                            LinearLayoutManager(
                                activity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        )


                        upcomingRecyclerView!!.setAdapter(
                            UpcomingEventAdapter(
                                upcomingActivitiesResponseData.data,
                                requireActivity(),
                                object : UpcomingEventAdapter.OnItemClickListener {
                                    override fun onItemClick() {
/*
                                        val action =
                                            HomeFragmentDirections.actionHomeFragmentToCharityDeatilsFragment("from home fragment")
                                        navController.navigate(action)*/
                                    }
                                })
                        )
                        arIndicatorView!!.attachTo(upcomingRecyclerView, true)
                       // arIndicatorView!!.numberOfIndicators = 5

                    } else {

                        Utility.showDialog(
                            requireContext(),
                            "Error !!",
                            "" + upcomingActivitiesResponseData.message,
                            "Ok",
                            "Cancel"
                        )
                        Utility.hideProgressDialog(requireContext())

                    }
                })

        } else {

            Utility.showDialog(
                requireContext(),
                "Network Error !!",
                "Please check your network connection.",
                "Ok",
                "Cancel"
            )

        }

    }



}