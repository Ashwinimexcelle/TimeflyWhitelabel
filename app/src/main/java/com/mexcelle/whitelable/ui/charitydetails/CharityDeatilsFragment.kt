package com.mexcelle.whitelable.ui.charitydetails

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.zxing.integration.android.IntentIntegrator
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.calander.Adapter.EventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.CharityDetailsViewModel
import kotlinx.android.synthetic.main.fragment_charity_deatils.*
import org.json.JSONException
import org.json.JSONObject
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import androidx.core.content.ContextCompat
import android.content.DialogInterface
import android.provider.Settings
import androidx.annotation.Nullable
import com.google.android.gms.maps.model.MarkerOptions
import com.google.zxing.integration.android.IntentResult

class CharityDeatilsFragment : Fragment(), OnMapReadyCallback {
    private val REQUEST_ID_MULTIPLE_PERMISSIONS: Int = 1001
    private var eventRecyclerview: RecyclerView? = null
    private var scanCodeBtn: TextView? = null
    private lateinit var adapter: EventAdapter
    private val args: CharityDeatilsFragmentArgs by navArgs()
    lateinit var charityDetailsViewModel: CharityDetailsViewModel
    var charityId: String = ""
    var activityId: String = ""
    var mapView: MapView? = null
    var map: GoogleMap? = null
    lateinit var mActivity: Activity
    var isGetActivityDetailsShown: Boolean = false
    var isJoinActivityShown: Boolean = false
    var isCancelActivityShown: Boolean = false
    var isStartActivityShown: Boolean = false
    var isStopActivityShown: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        charityDetailsViewModel = ViewModelProvider(this).get(CharityDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_charity_deatils, container, false)
        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isAdded && requireActivity() != null) {

            if (activity != null) {
                mActivity = this.requireActivity()
                init()
                charityId = arguments?.getString("charity_id")!!
                Log.e("charityId ", "charityId " + charityId);
                MainActivity.screenName.text = "Charity Details"
                mapView!!.onCreate(savedInstanceState)
                mapView!!.getMapAsync(this)

                // This function is called when the user accepts or decline the permission.
                // Request Code is used to check which permission called this function.
                // This request code is provided when the user is prompt for permission.
                @Override
                fun onRequestPermissionsResult(
                    requestCode: Int,
                    permissions: Array<String>,
                    grantResults: IntArray
                ) {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                    if (requestCode == 100) {
                        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(
                                mActivity,
                                "Camera Permission Granted",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(mActivity, "Camera Permission Denied", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else if (requestCode == 101) {
                        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(
                                mActivity,
                                "Storage Permission Granted",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                mActivity,
                                "Storage Permission Denied",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }

            }

        } else {

            Log.e("", "here in is not added");
        }

    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val locationPermissionRequest = registerForActivityResult(
           ActivityResultContracts.RequestMultiplePermissions()
       ) { permissions ->
           when {
               permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                   // Precise location access granted.
                   if(Utility.isLocationEnabled(mActivity)){


                   }else{

                       buildAlertMessageNoGps(mActivity)

                   }
               }
               permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                   // Only approximate location access granted.

                   if(Utility.isLocationEnabled(mActivity)){


                   }else{

                       buildAlertMessageNoGps(mActivity)

                   }
               }
               else -> {
                   // No location access granted.

               }
           }
       }


       locationPermissionRequest.launch(
           arrayOf(
               Manifest.permission.ACCESS_FINE_LOCATION,
               Manifest.permission.ACCESS_COARSE_LOCATION
           )
       )

       if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_CALENDAR)
           != PackageManager.PERMISSION_GRANTED
       ) {
           // Permission is not granted
           ActivityCompat.requestPermissions(
               requireActivity(),
               arrayOf(
                   Manifest.permission.ACCESS_FINE_LOCATION,
                   Manifest.permission.ACCESS_COARSE_LOCATION
               ),
               100
           );

       }

    }

    private fun getActivityDetails() {

        if (Utility.isOnline(mActivity)) {
            Utility.showProgressDialog(mActivity)
            Log.e("charityId ", "charityId " + charityId);
            charityDetailsViewModel.getCharityDetails(mActivity, charityId)!!
                .observe(viewLifecycleOwner, Observer { activityDetailsResponseData ->

                    if(!isGetActivityDetailsShown){

                        if (activityDetailsResponseData?.data != null) {

                            Utility.hideProgressDialog(mActivity)
                            Glide.with(this).load(activityDetailsResponseData.data[0].charity_logo)
                                .into(charity_logo);

                            Glide.with(this).load(activityDetailsResponseData.data[0].activity_image)
                                .into(event_image);
                            foundation_name.text = activityDetailsResponseData.data[0].activity_name
                            about_tv.text = activityDetailsResponseData.data[0].charity_description
                            cause_tv.text = activityDetailsResponseData.data[0].activity_name
                            date_tv.text = activityDetailsResponseData.data[0].activity_date
                            time_tv.text =
                                activityDetailsResponseData.data[0].activity_to_time + " - " + activityDetailsResponseData.data[0].activity_from_time

                            activityId = activityDetailsResponseData.data[0].activity_id

                            val sydney = LatLng(activityDetailsResponseData.data[0].activity_location_latitude.toDouble(),
                                activityDetailsResponseData.data[0].activity_location_longitude.toDouble())
                            map?.addMarker(
                                MarkerOptions()
                                    .position(sydney)
                                    .title(activityDetailsResponseData.data[0].activity_name)
                            )


                            map?.moveCamera(
                                CameraUpdateFactory.newLatLng(
                                    LatLng(
                                        activityDetailsResponseData.data[0].activity_location_latitude.toDouble(),
                                        activityDetailsResponseData.data[0].activity_location_longitude.toDouble()
                                    )
                                )
                            )
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
                                activityDetailsResponseData.data[0].activity_location_latitude.toDouble(),
                                activityDetailsResponseData.data[0].activity_location_longitude.toDouble()
                            ), 10F
                            ))

                            if( activityDetailsResponseData.data[0].join_status.equals("false")){

                                join_event_btn.visibility = View.VISIBLE
                                scan_code_btn.visibility = View.GONE
                                event_btn.visibility = View.GONE

                            }else{


                                if(activityDetailsResponseData.data[0].activity_track.equals("start")){

                                    scan_code_btn.text = "Start Event"

                                }else{

                                    scan_code_btn.text = "Stop Event"

                                }
                                join_event_btn.visibility = View.GONE
                                scan_code_btn.visibility = View.VISIBLE
                                event_btn.visibility = View.VISIBLE
                            }

                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + activityDetailsResponseData?.message,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(mActivity)

                        }

                        isGetActivityDetailsShown = true
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


    private fun joinActivity(activityId: String) {

        if (Utility.isOnline(mActivity)) {
            Utility.showProgressDialog(mActivity)
            charityDetailsViewModel.JoinActivity(mActivity,activityId)!!
                .observe(viewLifecycleOwner, Observer { joinActivityResponseData ->

                    if(!isJoinActivityShown){

                        if (joinActivityResponseData?.status.equals("success")) {

                            Utility.hideProgressDialog(mActivity)
                            join_event_btn.visibility = View.GONE
                            scan_code_btn.visibility = View.VISIBLE
                            event_btn.visibility = View.VISIBLE

                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + joinActivityResponseData?.message,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(mActivity)

                        }
                        isJoinActivityShown = true
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

    private fun unjoinActivity() {

        if (Utility.isOnline(mActivity)) {
            Utility.showProgressDialog(mActivity)
            charityDetailsViewModel.UnjoinActivity(mActivity)!!
                .observe(viewLifecycleOwner, Observer { joinActivityResponseData ->

/*
                    if(is)
*/

                    if (joinActivityResponseData?.status.equals("Success")) {

                        Utility.hideProgressDialog(mActivity)

                    } else {

                        Utility.showDialog(
                            mActivity,
                            "Error !!",
                            "" + joinActivityResponseData?.message,
                            "Ok",
                            "Cancel"
                        )
                        Utility.hideProgressDialog(mActivity)

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


    private fun startEvent(activityId: String,eventData: String) {

        if (Utility.isOnline(mActivity)) {
            Utility.showProgressDialog(mActivity)
            charityDetailsViewModel.StartEvent(mActivity, activityId,eventData)!!
                .observe(viewLifecycleOwner, Observer { startEventResponseData ->

                    if(!isStartActivityShown){

                        if (startEventResponseData?.status.equals("success")) {

                            Utility.hideProgressDialog(mActivity)
                            Utility.showDialog(
                                mActivity,
                                "Success !!",
                                "" + startEventResponseData?.message,
                                "Ok",
                                "Cancel"
                            )


                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + startEventResponseData?.message,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(mActivity)

                        }
                        isStartActivityShown = true
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

        MainActivity.screenName.text = "Charity"
        val navController = findNavController()
        mapView = activity!!.findViewById(R.id.mapView)
        Utility.setSolidFontAwesome(mActivity,calender_icon)
        Utility.setSolidFontAwesome(mActivity,time_icon)

        Utility.setSemibold(mActivity,about_t_tv)
        Utility.setSemibold(mActivity,cause_t_tv)
        Utility.setRegular(mActivity,foundnation_address)
        Utility.setSemibold(mActivity,location_tv)
        Utility.setSemibold(mActivity,foundation_name)
        Utility.setRegular(mActivity,about_tv)
        Utility.setRegular(mActivity,cause_tv)
        Utility.setRegular(mActivity,date_tv)
        Utility.setRegular(mActivity,time_tv)
        Utility.setSemibold(mActivity,event_btn)
        Utility.setSemibold(mActivity,scan_code_btn)
        Utility.setSemibold(mActivity,join_event_btn)
        Utility.setSemibold(mActivity,upcomming_event_tv_id)

        scanCodeBtn = activity!!.findViewById(R.id.scan_code_btn)
        eventRecyclerview = activity!!.findViewById(R.id.event_recylerview)
        eventRecyclerview!!.setOnFlingListener(null);
        scanCodeBtn!!.setOnClickListener {

            isStartActivityShown = false
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Scan QR code")
            integrator.setBeepEnabled(false)
            integrator.setCaptureActivity(CaptureActivityPortrait::class.java)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.initiateScan()
            scan_code_btn.text = "Stop Event"

        }

        join_event_btn!!.setOnClickListener {

            joinActivity(activityId)
        }

    }

    private fun buildAlertMessageNoGps(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Scanned : " + result.contents, Toast.LENGTH_LONG).show()
                startEvent(charityId,result.contents)
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {

        map = p0
        Log.e("Here", "Here 1112");
        map?.getUiSettings()?.setMyLocationButtonEnabled(true)
        map?.getUiSettings()?.setZoomControlsEnabled(true);
        map?.getUiSettings()?.setZoomControlsEnabled(true);
        map?.getUiSettings()?.setRotateGesturesEnabled(true);
        map?.getUiSettings()?.setScrollGesturesEnabled(true);
        map?.getUiSettings()?.setTiltGesturesEnabled(true);
        getActivityDetails()
        mapView?.onResume()


    }


    override fun onResume() {
        Log.i("", "onResume")
        super.onResume()

        if (map == null) {
            //mapView?.getMapAsync(this)
        } else {
        }
    }

}