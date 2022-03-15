package com.mexcelle.whitelable.ui.calander

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
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
import com.mexcelle.whitelable.model.CalendarwiseResponseData
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat

import java.util.*


class CalanderFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val TAG = "CalenderTest"
    private var couseSpinner: Spinner? = null
    var calenderEvent: CalenderEvent? = null
    var eventRecyclerview: RecyclerView? = null
    var cause: ArrayList<String> = ArrayList()
    var causeId: ArrayList<String> = ArrayList()
    var selectedCauseId: String = ""
    var selectedDate: String = ""
    lateinit var calendarViewModel: CalendarViewModel
    lateinit var mActivity: Activity
    var isShown: Boolean = false
    var isCauseShown: Boolean = false


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        calendarViewModel = ViewModelProvider(getActivity()!!).get(CalendarViewModel::class.java)

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_calander, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        if (isAdded && requireActivity() != null) {

            if (activity != null) {
                mActivity = this.requireActivity()
                Log.e("", "here in is added");
                init()
                //getCause()
            }

        } else {

            Log.e("", "here in is not added");
        }


    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {

        MainActivity.bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        selectedDate = currentDate.toString()


        MainActivity.screenName.text = "Calender"
        couseSpinner = activity!!.findViewById(R.id.couse_spinner)
        couseSpinner?.setOnItemSelectedListener(this);
        calenderEvent = activity!!.findViewById(R.id.calender_event)
        eventRecyclerview = activity!!.findViewById(R.id.event_recyclerview)


        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val event = Event(calendar.timeInMillis, "Test")
        calenderEvent!!.initCalderItemClickCallback { dayContainerModel ->

            Log.e("here111", "here111");
            Log.e(TAG, dayContainerModel.date)
            var x = Utility.getparsedDate(dayContainerModel.date)
            selectedDate = x.toString()
            var y = Utility.isDateIsOld(x.toString())
            Log.e(TAG, x.toString())
            Log.e(TAG, y.toString())

            if (Utility.isDateIsOld(x.toString()) == true) {


                Utility.showDialog(
                    mActivity, "Invalid Date", "Please Select Valid Date",
                    "Ok", "Cancel"
                )
            } else {

                //calendarViewModel.observeData().removeObservers(this);
                Log.e("Here 1122", "Here 1122")
                isShown = false
                //calendarViewModel.getCalendarwiseActivities(mActivity,selectedDate,selectedCauseId)?.removeObserver(Observer { CalendarwiseResponseData ->})
                getCalendarwiseActivities(selectedDate, selectedCauseId)
            }


        }

        couseSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                selectedCauseId = causeId.get(position)

                Log.e("here 1", "here 1");
                Log.e("here 1", "here 1" + selectedDate);
                Log.e("here 1", "here 1" + selectedCauseId);


                if (selectedDate.length > 0) {


                    isShown = false

                    getCalendarwiseActivities(selectedDate, selectedCauseId)

                }


            }

        }
        calenderEvent!!.addEvent(event)
        getCause()
    }

    private fun getCalendarwiseActivities(selectedDate: String, selectedCause: String) {

        if (Utility.isOnline(mActivity)) {

            Utility.showProgressDialog(mActivity)

            calendarViewModel.getCalendarwiseActivities(
                mActivity,
                selectedDate,
                selectedCause
            )!!
                .observe(viewLifecycleOwner, Observer { CalendarwiseResponseData ->


                    if (!isShown) {


                        Log.e("Here 1110", "Here 1110")
                        isShown = true

                        if (CalendarwiseResponseData?.status.equals("failed")) {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + "" + CalendarwiseResponseData?.message,
                                "Ok",
                                "Cancel"
                            )

                            Utility.hideProgressDialog(mActivity)
                        } else {

                            if (CalendarwiseResponseData?.data?.size!! > 0) {
                                eventRecyclerview!!.setOnFlingListener(null);
                                eventRecyclerview!!.setLayoutManager(
                                    LinearLayoutManager(
                                        activity,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                )
                                eventRecyclerview!!.setAdapter(
                                    EventAdapter(CalendarwiseResponseData.data,
                                        mActivity,
                                        object : EventAdapter.OnItemClickListener {
                                            override fun onItemClick() {


                                            }
                                        })
                                )


                            } else {

                                Utility.showDialog(
                                    mActivity,
                                    "Error !!",
                                    "" + "No Activities present for selected date",
                                    "Ok",
                                    "Cancel"
                                )
                            }

                            Utility.hideProgressDialog(mActivity)

                        }


                    }


                }

                )

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


    private fun getCause() {

        if (Utility.isOnline(mActivity)) {

            Utility.showProgressDialog(mActivity)
            calendarViewModel.getCause(mActivity)!!

                .observe(viewLifecycleOwner, Observer { causeResponseData ->

                    if (!isCauseShown) {

                        isCauseShown = true
                        if (causeResponseData?.data?.size!! > 0) {

                            cause.clear()
                            for (i in 1..causeResponseData?.data?.size - 1 step 1) {

                                cause.add(causeResponseData?.data?.get(i).name)
                                causeId.add(causeResponseData?.data?.get(i).id)
                            }


                            val adap: ArrayAdapter<*> =
                                ArrayAdapter<String>(mActivity, R.layout.spinner_item, cause)
                            adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            couseSpinner!!.setAdapter(adap)

                            Utility.hideProgressDialog(mActivity)


                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + "No Activities present for selected date",
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


}

