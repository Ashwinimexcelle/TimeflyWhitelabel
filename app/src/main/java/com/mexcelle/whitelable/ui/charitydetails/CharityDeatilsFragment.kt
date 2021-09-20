package com.mexcelle.whitelable.ui.charitydetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.calander.Adapter.EventAdapter
import com.mexcelle.whitelable.ui.charity.CharityFragmentDirections
import com.mexcelle.whitelable.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_charity_deatils.*


class CharityDeatilsFragment : Fragment() {
    private var eventRecyclerview: RecyclerView?=null
    private var scanCodeBtn:TextView?=null
    private lateinit var adapter: EventAdapter
    private val args:CharityDeatilsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_charity_deatils, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.screenName.text="Charity Details"

        init()



    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {
        MainActivity.screenName.text="Charity"
        val  navController=findNavController()


        scanCodeBtn = activity!!.findViewById(R.id.scan_code_btn)
        eventRecyclerview = activity!!.findViewById(R.id.event_recylerview)
        eventRecyclerview!!.setOnFlingListener(null);

        //   upcomingRecyclerView!!.setNestedScrollingEnabled(false);


        scanCodeBtn!!.setOnClickListener {
            val action =CharityDeatilsFragmentDirections.actionCharityDeatilsFragmentToScanCodeFragment()
            navController.navigate(action)
        }

        eventRecyclerview!!.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))


        eventRecyclerview!!.setAdapter(EventAdapter(activity!!, object : EventAdapter.OnItemClickListener {
            override fun onItemClick() {
                val action = CharityFragmentDirections.actionCharityFragmentToCharityDeatilsFragment("from charity fragment")
                navController.navigate(action)



            }
        }))

}
}