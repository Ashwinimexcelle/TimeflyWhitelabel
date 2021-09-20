package com.mexcelle.whitelable.ui.calander

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.calander.Adapter.EventAdapter
import com.mexcelle.whitelable.ui.home.Adapter.UpcomingEventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.skyhope.eventcalenderlibrary.CalenderEvent
import com.skyhope.eventcalenderlibrary.model.Event
import java.util.*


class CalanderFragment : Fragment() {

    private val TAG = "CalenderTest"
    private  var couseSpinner: Spinner?=null
    var calenderEvent: CalenderEvent?=null
    var eventRecyclerview: RecyclerView?=null
    var cause = arrayOf(
            "Cause 1", "Cause 2", "Cause 3", "Cause 4"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calander, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()


    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {

        MainActivity.screenName.text="Calender"



        couseSpinner = activity!!.findViewById(R.id.couse_spinner)
        val adap: ArrayAdapter<*> = ArrayAdapter<String>(activity!!, R.layout.spinner_item, cause)
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        couseSpinner!!.setAdapter(adap)

         calenderEvent = activity!!.findViewById(R.id.calender_event)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val event = Event(calendar.timeInMillis, "Test")
        calenderEvent!!.addEvent(event)

        calenderEvent!!.initCalderItemClickCallback { dayContainerModel ->
            Log.d(TAG, dayContainerModel.date)

        }


        eventRecyclerview = activity!!.findViewById(R.id.event_recyclerview)
        eventRecyclerview!!.setOnFlingListener(null);
        eventRecyclerview!!.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))
        eventRecyclerview!!.setAdapter(EventAdapter(activity!!, object : EventAdapter.OnItemClickListener {
            override fun onItemClick() {



            }
        }))

    }

}