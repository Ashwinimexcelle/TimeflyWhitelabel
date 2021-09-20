package com.mexcelle.whitelable.ui.profle

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.ui.profle.Adapter.ProfileCompleteEventsAdapter
import com.mexcelle.whitelable.ui.profle.Adapter.ProfileUpcommingEventsAdapter


class ProfileFragment : Fragment() {

    private  var profileUpcomingEventRecyclerView: RecyclerView?=null
    private  var profileCompleteEventRecyclerView: RecyclerView?=null
    private  var editImageView: ImageView?=null
    private  var ageSpinner: Spinner?=null
    private  var genderSpinner: Spinner?=null
    private  var bioEditText: EditText?=null
    private  var companyNameEditText: EditText?=null
    private  var designationEditText: EditText?=null
    private  var emailEditText: EditText?=null
    private var edit = true

    var age = arrayOf("1", "2", "3", "4")
    var gender = arrayOf("Male", "Female", "Others")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_profile, container, false)
        return  root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        disableField()

    }

    private fun enableField() {

        bioEditText!!.setEnabled(true);
        emailEditText!!.setEnabled(true);
        emailEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);
        companyNameEditText!!.setEnabled(true);
        companyNameEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);
        designationEditText!!.setEnabled(true);
        designationEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);
        emailEditText!!.setEnabled(true);
        emailEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);
        ageSpinner!!.visibility=View.VISIBLE
        genderSpinner!!.visibility=View.VISIBLE
    }

    private fun disableField() {


        bioEditText!!.setEnabled(false);
        emailEditText!!.setEnabled(false);
        emailEditText!!.setBackgroundColor(resources.getColor(R.color.white))
        companyNameEditText!!.setEnabled(false);
        companyNameEditText!!.setBackgroundColor(resources.getColor(R.color.white))
        emailEditText!!.setEnabled(false);
        emailEditText!!.setBackgroundColor(resources.getColor(R.color.white))
        designationEditText!!.setEnabled(false);
        designationEditText!!.setBackgroundColor(resources.getColor(R.color.white))

        ageSpinner!!.visibility=View.GONE
        genderSpinner!!.visibility=View.GONE

    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {
        MainActivity.screenName.text="Profile"

        bioEditText=activity!!.findViewById(R.id.bio_et)
        companyNameEditText=activity!!.findViewById(R.id.company_id)
        emailEditText=activity!!.findViewById(R.id.email_tv)
        designationEditText=activity!!.findViewById(R.id.designation_id)

        editImageView=activity!!.findViewById(R.id.edit_img)
        ageSpinner = activity!!.findViewById(R.id.age_sp)
        genderSpinner = activity!!.findViewById(R.id.gender_sp)


        val adapt: ArrayAdapter<*> = ArrayAdapter<String>(activity!!, R.layout.spinner_item, age)
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner!!.setAdapter(adapt)

        val adap: ArrayAdapter<*> = ArrayAdapter<String>(activity!!, R.layout.spinner_item, gender)
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner!!.setAdapter(adap)


        editImageView!!.setOnClickListener {

      if (edit)
      {
          enableField()
          edit=false
      }
            else
      {
          disableField()
          edit=true
      }
        }

        profileCompleteEventRecyclerView=activity!!.findViewById(R.id.profile_complete_event_rv)
        profileCompleteEventRecyclerView!!.setOnFlingListener(null);

        profileUpcomingEventRecyclerView=activity!!.findViewById(R.id.profile_upcomming_event_rv)
        profileUpcomingEventRecyclerView!!.setOnFlingListener(null);

        //   upcomingRecyclerView!!.setNestedScrollingEnabled(false);

        profileUpcomingEventRecyclerView!!.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false))
        profileUpcomingEventRecyclerView!!.setAdapter(ProfileUpcommingEventsAdapter(activity!!, object : ProfileUpcommingEventsAdapter.OnItemClickListener {
            override fun onItemClick() {


            }
        }))


        profileCompleteEventRecyclerView!!.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false))


        profileCompleteEventRecyclerView!!.setAdapter(ProfileCompleteEventsAdapter(activity!!))
    }



}