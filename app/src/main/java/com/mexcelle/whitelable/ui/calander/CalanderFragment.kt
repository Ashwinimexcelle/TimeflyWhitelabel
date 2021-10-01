package com.mexcelle.whitelable.ui.calander

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.calander.Adapter.EventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.CalendarViewModel
import com.skyhope.eventcalenderlibrary.CalenderEvent
import com.skyhope.eventcalenderlibrary.model.Event
import androidx.lifecycle.Observer

import java.util.*


class CalanderFragment : Fragment() {

    private val TAG = "CalenderTest"
    private var couseSpinner: Spinner? = null
    var calenderEvent: CalenderEvent? = null
    var eventRecyclerview: RecyclerView? = null
    var cause = arrayOf(
        "Cause 1", "Cause 2", "Cause 3", "Cause 4"
    )

    lateinit var calendarViewModel: CalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        calendarViewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_calander, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()



    }

    private fun getCalendarwiseActivities() {

        if (Utility.isOnline(requireActivity())) {

            Utility.showProgressDialog(requireActivity())
            calendarViewModel.getCalendarwiseActivities(requireActivity())!!
                .observe(requireActivity(), Observer { calendarwiseResponseData ->

                    if (calendarwiseResponseData?.data?.size!! > 0) {


                        eventRecyclerview!!.setOnFlingListener(null);
                        eventRecyclerview!!.setLayoutManager(
                            LinearLayoutManager(
                                activity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        )
                        eventRecyclerview!!.setAdapter(
                            EventAdapter(calendarwiseResponseData.data,
                                requireActivity(),
                                object : EventAdapter.OnItemClickListener {
                                    override fun onItemClick() {


                                    }
                                })
                        )


                    } else {

                        Utility.showDialog(
                            requireContext(),
                            "Error !!",
                            "" + "No Activities present for selected date",
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

        MainActivity.screenName.text = "Calender"
        couseSpinner = activity!!.findViewById(R.id.couse_spinner)
        val adap: ArrayAdapter<*> = ArrayAdapter<String>(activity!!, R.layout.spinner_item, cause)
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        couseSpinner!!.setAdapter(adap)
        calenderEvent = activity!!.findViewById(R.id.calender_event)
        eventRecyclerview = activity!!.findViewById(R.id.event_recyclerview)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val event = Event(calendar.timeInMillis, "Test")
        calenderEvent!!.addEvent(event)

        calenderEvent!!.initCalderItemClickCallback { dayContainerModel ->
            Log.e(TAG, dayContainerModel.date)

        }

        getCalendarwiseActivities()



    }


}