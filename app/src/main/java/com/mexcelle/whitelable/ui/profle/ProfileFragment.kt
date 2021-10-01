package com.mexcelle.whitelable.ui.profle

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.databinding.ProfileFragmentBinding
import com.mexcelle.whitelable.ui.home.Adapter.UpcomingEventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.ui.profle.Adapter.CompletedActivitiesAdapter
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(),AdapterView.OnItemSelectedListener {

    private var profileUpcomingEventRecyclerView: RecyclerView? = null
    private var profileCompleteEventRecyclerView: RecyclerView? = null
    private var editImageView: ImageView? = null
    private var ageSpinner: Spinner? = null
    private var genderSpinner: Spinner? = null
    private var bioEditText: EditText? = null
    private var usernameEditText: EditText? = null
    private var companyNameEditText: EditText? = null
    private var designationEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var edit = true

    var age = arrayOf("1", "2", "3", "4")
    var gender = arrayOf("Male", "Female", "Others")
    lateinit var profileViewModel: ProfileViewModel
    lateinit var binding: ProfileFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater, com.mexcelle.whitelable.R.layout.fragment_profile, container, false
        )
        binding.setLifecycleOwner(this)
        binding.profileViewModel = profileViewModel
        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        disableField()
        profileViewModel.updateSpinnerList(requireContext())
        getProfile();

        ageSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                Log.e("here ","here ");
                Log.e("position ","position "+position);
                Log.e("position ","position "+ profileViewModel.ageList[position]);


                profileViewModel.age = profileViewModel.ageList[position]
                age_id.setText(profileViewModel.ageList[position])
                age_id.text = profileViewModel.ageList[position]
                Log.e("age_id ","age_id "+age_id.text);
            }
        }



        genderSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                Log.e("here 1","here 1");

                profileViewModel.gender = profileViewModel.genderList[position]
                gender_id.text = profileViewModel.genderList[position]
                gender_id.setText(profileViewModel.genderList[position])
                Log.e("gender_id ","gender_id "+gender_id.text);

            }

        }


    }

    private fun getProfile() {
        if (Utility.isOnline(requireActivity())) {

            Utility.showProgressDialog(requireActivity())
            profileViewModel.getProfile(requireActivity())!!
                .observe(requireActivity(), Observer { profileResponseData ->

                    if (profileResponseData?.data?.size!! > 0) {

                        Utility.hideProgressDialog(requireActivity())
                        //UODATE UI
                        profileViewModel.age = profileResponseData.data[0].birth_date
                        profileViewModel.username = profileResponseData.data[0].name
                        profileViewModel.bio = profileResponseData.data[0].bio
                        profileViewModel.companyName = profileResponseData.data[0].company_name
                        profileViewModel.emailId = profileResponseData.data[0].email_id
                        emailEditText?.setText(profileResponseData.data[0].email_id)

                        getUpcomingActivities()



                    } else {

                        Utility.showDialog(
                            requireContext(),
                            "Error !!",
                            "" + profileResponseData.message,
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


    private fun updateProfile() {
        if (Utility.isOnline(requireActivity())) {

            Utility.showProgressDialog(requireActivity())
            profileViewModel.updateProfile(requireActivity())!!
                .observe(requireActivity(), Observer { profileUpdateResponseData ->

                    if (profileUpdateResponseData?.status.equals("Success")) {

                        Utility.hideProgressDialog(requireActivity())
                        //UODATE UI



                    } else {

                        Utility.showDialog(
                            requireContext(),
                            "Error !!",
                            "" + profileUpdateResponseData?.message,
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
            Log.e("Here 1","Here 1")

            profileViewModel.getUpcomingActivties(requireActivity())!!
                .observe(requireActivity(), Observer { upcomingActivitiesResponseData ->

                    Log.e("Here 2","Here 2")

                    if (upcomingActivitiesResponseData?.data?.size!! > 0) {

                        Utility.hideProgressDialog(requireActivity())

                        profileUpcomingEventRecyclerView = requireActivity().findViewById(R.id.profile_upcomming_event_rv)
                        profileUpcomingEventRecyclerView!!.setOnFlingListener(null);
                        profileUpcomingEventRecyclerView!!.setLayoutManager(
                            LinearLayoutManager(
                                activity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        )


                        profileUpcomingEventRecyclerView!!.setAdapter(
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
                        getCompletedActivities()
                       // arIndicatorView!!.attachTo(upcomingRecyclerView, true)
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


    private fun getCompletedActivities() {

        if (Utility.isOnline(requireActivity())) {

            Utility.showProgressDialog(requireActivity())
            profileViewModel.getCompletedActivities(requireActivity())!!
                .observe(requireActivity(), Observer { completedActivitiesResponseData ->

                    if (completedActivitiesResponseData?.data?.size!! > 0) {

                        Utility.hideProgressDialog(requireActivity())

                        profileCompleteEventRecyclerView = requireActivity().findViewById(R.id.profile_complete_event_rv)
                        profileCompleteEventRecyclerView!!.setOnFlingListener(null);
                        profileCompleteEventRecyclerView!!.setLayoutManager(
                            LinearLayoutManager(
                                activity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        )


                        profileCompleteEventRecyclerView!!.setAdapter(
                            CompletedActivitiesAdapter(
                                completedActivitiesResponseData.data,
                                requireActivity(),
                                object : CompletedActivitiesAdapter.OnItemClickListener {
                                    override fun onItemClick() {



                                    }
                                })
                        )
                        // arIndicatorView!!.attachTo(upcomingRecyclerView, true)
                        // arIndicatorView!!.numberOfIndicators = 5

                    } else {

                        Utility.showDialog(
                            requireContext(),
                            "Error !!",
                            "" + completedActivitiesResponseData.message,
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

    private fun enableField() {

        bioEditText!!.setEnabled(true);
        bioEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);


        usernameEditText!!.setEnabled(true);
        usernameEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        companyNameEditText!!.setEnabled(true);
        companyNameEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        designationEditText!!.setEnabled(true);
        designationEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        emailEditText!!.setEnabled(true);
        emailEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        ageSpinner!!.visibility = View.VISIBLE
        genderSpinner!!.visibility = View.VISIBLE

        age_id!!.visibility = View.GONE
        gender_id!!.visibility = View.GONE

    }

    private fun disableField() {


        bioEditText!!.setEnabled(false);
        bioEditText!!.setBackgroundColor(resources.getColor(R.color.white))


        usernameEditText!!.setEnabled(false);
        usernameEditText!!.setBackgroundColor(resources.getColor(R.color.white))

        companyNameEditText!!.setEnabled(false);
        companyNameEditText!!.setBackgroundColor(resources.getColor(R.color.white))

        emailEditText!!.setEnabled(false);
        emailEditText!!.setBackgroundColor(resources.getColor(R.color.white))

        designationEditText!!.setEnabled(false);
        designationEditText!!.setBackgroundColor(resources.getColor(R.color.white))

        ageSpinner!!.visibility = View.GONE
        genderSpinner!!.visibility = View.GONE

        age_id!!.visibility = View.VISIBLE
        gender_id!!.visibility = View.VISIBLE

    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {
        MainActivity.screenName.text = "Profile"

        usernameEditText = activity!!.findViewById(R.id.user_name_tv)
        bioEditText = activity!!.findViewById(R.id.bio_et)
        companyNameEditText = activity!!.findViewById(R.id.company_id)
        emailEditText = activity!!.findViewById(R.id.email_tv)
        designationEditText = activity!!.findViewById(R.id.designation_id)

        editImageView = activity!!.findViewById(R.id.edit_img)
        ageSpinner = activity!!.findViewById(R.id.age_sp)
        genderSpinner = activity!!.findViewById(R.id.gender_sp)

        ageSpinner?.setOnItemSelectedListener(this);
        genderSpinner?.setOnItemSelectedListener(this);


        val adapt: ArrayAdapter<*> = ArrayAdapter<String>(activity!!, R.layout.spinner_item, age)
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner!!.setAdapter(adapt)
        val adap: ArrayAdapter<*> = ArrayAdapter<String>(activity!!, R.layout.spinner_item, gender)
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner!!.setAdapter(adap)

        editImageView!!.setOnClickListener {

            if (edit) {
                enableField()
                edit = false
            } else {

                Log.e("AGE","age_id"+age_id.text);
                Log.e("GENDER","age_id"+gender_id.text);

                updateProfile()
                //disableField()
                //edit = true
            }
        }

        //profileCompleteEventRecyclerView = activity!!.findViewById(R.id.profile_complete_event_rv)
        //profileCompleteEventRecyclerView!!.setOnFlingListener(null);

        /*profileUpcomingEventRecyclerView = activity!!.findViewById(R.id.profile_upcomming_event_rv)
        profileUpcomingEventRecyclerView!!.setOnFlingListener(null);

        profileUpcomingEventRecyclerView!!.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        profileUpcomingEventRecyclerView!!.setAdapter(
            ProfileUpcommingEventsAdapter(
                activity!!,
                object : ProfileUpcommingEventsAdapter.OnItemClickListener {
                    override fun onItemClick() {


                    }
                })
        )
*/

       /* profileCompleteEventRecyclerView!!.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )*/


       // profileCompleteEventRecyclerView!!.setAdapter(ProfileCompleteEventsAdapter(activity!!))
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

/*
        if()
*/


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {


    }


}