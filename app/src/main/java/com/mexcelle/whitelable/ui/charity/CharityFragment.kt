package com.mexcelle.whitelable.ui.charity

import android.annotation.SuppressLint
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
import com.mexcelle.whitelable.ui.calander.Adapter.EventAdapter
import com.mexcelle.whitelable.ui.charity.adapter.CharityAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.CharityViewModel

class CharityFragment : Fragment() {
    private lateinit var navController: NavController
    var charityRecyclerview: RecyclerView? = null
    private lateinit var adapter: EventAdapter
    lateinit var charityViewModel: CharityViewModel


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
        init()
        getUpcomingActivities()

        Log.e("method call", "onActivityCreated")
    }

    private fun getUpcomingActivities() {


        if (Utility.isOnline(requireActivity())) {
            Utility.showProgressDialog(requireActivity())
            charityViewModel.getUpcomingActivities(requireActivity())!!
                .observe(requireActivity(), Observer { upcomingActivitiesResponseData ->

                    if (upcomingActivitiesResponseData?.data?.size!! > 0) {

                        Utility.hideProgressDialog(requireActivity())



                        charityRecyclerview!!.setLayoutManager(
                            LinearLayoutManager(
                                requireActivity(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        )


                        charityRecyclerview!!.setAdapter(
                            CharityAdapter(
                                upcomingActivitiesResponseData.data,
                                requireActivity(),
                                object : CharityAdapter.OnItemClickListener {
                                    override fun onItemClick() {
                                        val action =
                                            CharityFragmentDirections.actionCharityFragmentToCharityDeatilsFragment(
                                                "from charity fragment"
                                            )
                                        navController.navigate(action)


                                    }
                                })
                        )

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

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {

        MainActivity.screenName.text = "Charity"
        navController = findNavController()
        charityRecyclerview = activity!!.findViewById(R.id.charirty_recylerview)
        charityRecyclerview!!.setOnFlingListener(null);

    }

}