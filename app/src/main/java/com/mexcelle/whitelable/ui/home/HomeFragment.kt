package com.mexcelle.whitelable.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.GridView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arindicatorview.ARIndicatorView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.model.UpcomingActivitiesResponseDataDetails
import com.mexcelle.whitelable.ui.home.Adapter.CauseAdapterAdapter
import com.mexcelle.whitelable.ui.home.Adapter.UpcomingEventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_join_charity.*
import okhttp3.internal.notifyAll


class HomeFragment : Fragment() {

    private var causeAdapter: CauseAdapterAdapter? = null
    private lateinit var navController: NavController
    private var charityButton: Button? = null
    private var upcomingRecyclerView: RecyclerView? = null
    private var causeRecyclerView: RecyclerView? = null
    private var chooseChairtyGridView: GridView? = null
    private var arIndicatorView: ARIndicatorView? = null
    lateinit var homeViewModel: HomeViewModel
    lateinit var mActivity: Activity
    var isUpcomingActivitiesShown: Boolean = false
    var isCauseShown: Boolean = false




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //isCauseShown = false
       // isUpcomingActivitiesShown = false


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("in INIT","in INIT view created");



    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {

        Log.e("in INIT","in INIT");
        MainActivity.bottomNavigationView.getMenu().setGroupCheckable(0, true, true);

        navController = findNavController()
        MainActivity.screenName.text = "Home"

        val window = mActivity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        arIndicatorView = mActivity.findViewById(R.id.ar_indicator)
        Utility.setSemibold(mActivity,text_home)
        Utility.setSemibold(mActivity,charity_tv)
        causeRecyclerView = mActivity!!.findViewById(R.id.cause_rv)
        //isCauseShown = false
        getCause()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(isAdded && requireActivity() != null){

            if(activity != null){
                mActivity = requireActivity()
                Log.e("","here in is added");
                init()

            }

        }else{

            Log.e("","here in is not added");

        }
        Log.e("in INIT","in INIT activity created");


    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun getCause() {

        Log.e("isCauseShown","isCauseShown "+isCauseShown);
        if(mActivity != null){

            if (Utility.isOnline(mActivity)) {

                Utility.showProgressDialog(mActivity)
                homeViewModel.getCause(mActivity)!!
                    .observe(viewLifecycleOwner, Observer { causeResponseData ->

                        Utility.hideProgressDialog(mActivity)
                        if(!isCauseShown){

                            causeAdapter =
                                CauseAdapterAdapter(
                                    causeResponseData!!.data,
                                    mActivity,
                                    object : CauseAdapterAdapter.OnItemClickListener {
                                        override fun onItemClick() {

                                            Log.e("Here in click","Here in click");
                                            navController.navigate(R.id.charityFragment)


                                        }
                                    })
                            causeRecyclerView!!.layoutManager = GridLayoutManager(mActivity,3)
                            causeRecyclerView!!.setAdapter(null)
                            causeRecyclerView!!.setAdapter(causeAdapter)
                            isCauseShown = true
                            getUpcomingActivities();
                        }


                    })

            } else {

                Utility.showDialog(
                    mActivity,
                    "Network Error !!",
                    "Please check your network connection.",
                    "Ok",
                    "Cancel"
                )
            }
        }

    }

    private fun getUpcomingActivities() {

        if (Utility.isOnline(mActivity)) {
            Log.e("isCauseShown","isUpcomingActivitiesShown "+isUpcomingActivitiesShown);

           // Utility.showProgressDialog(requireActivity())
            homeViewModel.getUpcomingActivties(mActivity)!!
                .observe(viewLifecycleOwner, Observer { upcomingActivitiesResponseData ->


                    if(!isUpcomingActivitiesShown){

                        isUpcomingActivitiesShown = true
                        if (upcomingActivitiesResponseData?.data?.size!! > 0) {

                            Utility.hideProgressDialog(mActivity)

                            upcomingRecyclerView = mActivity.findViewById(R.id.upcomming_activities_rv)
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
                                    mActivity,
                                    object : UpcomingEventAdapter.OnItemClickListener {
                                        override fun onItemClick(upcomingActivitiesList: UpcomingActivitiesResponseDataDetails?) {
                                            MainActivity.bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
                                            Log.e("Here ","Here in click");
                                            val action = HomeFragmentDirections.actionHomeFragmentToCharityDeatilsFragment("from home fragment",
                                                upcomingActivitiesList?.id!!
                                            )
                                            var bundle = bundleOf("charity_id" to upcomingActivitiesList?.charity_id )
                                            // bundle.putString("charity_is",upcomingActivitiesList?.charity_id )
                                            navController.navigate(action)


                                        }
                                    })
                            )
                            arIndicatorView!!.attachTo(upcomingRecyclerView, true)
                            // arIndicatorView!!.numberOfIndicators = 5
                            arIndicatorView!!.visibility

                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "No Upcoming Events" ,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(mActivity)

                        }
                    }

                })

        } else {

            Utility.showDialog(
                 mActivity,
                "Network Error !!",
                "Please check your network connection.",
                "Ok",
                "Cancel"
            )

        }

    }



}