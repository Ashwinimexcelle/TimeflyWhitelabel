package com.mexcelle.whitelable.ui.charity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.model.UpcomingActivitiesResponseDataDetails
import com.mexcelle.whitelable.ui.calander.Adapter.EventAdapter
import com.mexcelle.whitelable.ui.charity.adapter.CharityAdapter
import com.mexcelle.whitelable.ui.home.Adapter.UpcomingEventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.CharityViewModel

class CharityFragment : Fragment() {
    private lateinit var navController: NavController
    var charityRecyclerview: RecyclerView? = null
    private lateinit var adapter: EventAdapter
    lateinit var charityViewModel: CharityViewModel
    lateinit var mActivity: Activity
    var isGetUpcomingActivitiesShown: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        charityViewModel = ViewModelProvider(this).get(CharityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_charity, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       /* init()
        getUpcomingActivities()*/

        Log.e("method call", "onActivityCreated")

        if(isAdded && requireActivity() != null){

            if(activity != null){
                mActivity = requireActivity()
                Log.e("","here in is added");
                init()
            }

        }else{

            Log.e("","here in is not added");

        }


    }

    private fun getUpcomingActivities() {


        if (Utility.isOnline(mActivity)) {
            Utility.showProgressDialog(mActivity)
            charityViewModel.getUpcomingActivities(mActivity)!!
                .observe(viewLifecycleOwner, Observer { upcomingActivitiesResponseData ->


                    if(!isGetUpcomingActivitiesShown){

                        if (upcomingActivitiesResponseData?.data?.size!! > 0) {

                            Utility.hideProgressDialog(mActivity)



                            charityRecyclerview!!.setLayoutManager(
                                LinearLayoutManager(
                                    mActivity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            )


                            charityRecyclerview!!.setAdapter(
                                CharityAdapter(
                                    upcomingActivitiesResponseData.data,
                                    mActivity,
                                    object : CharityAdapter.OnItemClickListener {
                                        override fun onItemClick(upcomingActivitiesList: UpcomingActivitiesResponseDataDetails?) {
                                            val action =
                                                CharityFragmentDirections.actionCharityFragmentToCharityDeatilsFragment(
                                                    "from charity fragment",
                                                    upcomingActivitiesList?.id!!)
                                            navController.navigate(action)


                                        }
                                    })
                            )

                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + upcomingActivitiesResponseData.message,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(mActivity)

                        }

                        isGetUpcomingActivitiesShown = true
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

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {

        MainActivity.bottomNavigationView.getMenu().setGroupCheckable(0, true, true);

        MainActivity.screenName.text = "Charity List"
        navController = findNavController()
        charityRecyclerview = activity!!.findViewById(R.id.charirty_recylerview)
        charityRecyclerview!!.setOnFlingListener(null);

        getUpcomingActivities()


    }

}