package com.mexcelle.whitelable.ui.profle

import android.Manifest
import android.R.attr
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.databinding.ProfileFragmentBinding
import com.mexcelle.whitelable.model.UpcomingActivitiesResponseDataDetails
import com.mexcelle.whitelable.ui.home.Adapter.UpcomingEventAdapter
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.ui.profle.Adapter.CompletedActivitiesAdapter
import com.mexcelle.whitelable.util.Utility
import com.mexcelle.whitelable.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import android.widget.Toast

import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.MediaStore

import android.content.Intent

import android.content.DialogInterface

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.startActivityForResult
import android.net.Uri
import android.database.Cursor
import android.graphics.Bitmap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.mexcelle.whitelable.model.UserProfileUploadImageRequestModel
import com.mexcelle.whitelable.retrofit.ApiInterface
import id.zelory.compressor.Compressor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import java.io.*
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import android.content.Context
import android.R.attr.data
import android.provider.MediaStore.Images
import com.mexcelle.whitelable.ui.profle.Adapter.ProfileUpcommingEventsAdapter

import java.io.ByteArrayOutputStream











class ProfileFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var finalFile: File
    private lateinit var profileFile: File
    private lateinit var compressedImageFile: File
    private var selectedImagePath: String? = ""
    private val SELECT_PICTURE: Int = 1001
    private val CAPTURE_PICTURE: Int = 1002

    lateinit var mActivity: Activity
    private var profileUpcomingEventRecyclerView: RecyclerView? = null
    private var profileCompleteEventRecyclerView: RecyclerView? = null
    private var editImageView: TextView? = null
    private var ageSpinner: Spinner? = null
    private var genderSpinner: Spinner? = null
    private var bioEditText: EditText? = null
    private var usernameEditText: EditText? = null
    private var companyNameEditText: EditText? = null
    private var designationEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var edit = true
    lateinit var profileViewModel: ProfileViewModel
    lateinit var binding: ProfileFragmentBinding
    var isGetProfileShown: Boolean = false
    var isUpdateProfileShown: Boolean = false
    var isGetUpcomingActivitiesShown: Boolean = false
    var isGetPastActivitiesShown: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )
        binding.setLifecycleOwner(this)
        binding.profileViewModel = profileViewModel
        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (isAdded && requireActivity() != null) {

            if (activity != null) {
                mActivity = this.requireActivity()
                profileViewModel.updateSpinnerList(mActivity)
                init()
                ageSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        profileViewModel.age = profileViewModel.ageList[position]
                        age_id.setText(profileViewModel.ageList[position])
                        age_id.text = profileViewModel.ageList[position]
                    }
                }

                genderSpinner?.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {

                            Log.e("here 1", "here 1");

                            profileViewModel.gender = profileViewModel.genderList[position]
                            gender_id.text = profileViewModel.genderList[position]
                            gender_id.setText(profileViewModel.genderList[position])
                            Log.e("gender_id ", "gender_id " + gender_id.text);

                        }

                    }


                //getCause()
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
                permissions.getOrDefault(Manifest.permission.READ_EXTERNAL_STORAGE, false) -> {
                    // Precise location access granted.

                }
                permissions.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false) -> {
                    // Only approximate location access granted.


                }
                permissions.getOrDefault(Manifest.permission.CAMERA, false) -> {
                    // Only approximate location access granted.


                }
                else -> {
                    // No location access granted.

                }
            }
        }


        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA

            )
        )

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA

                ),
                100
            );

        }

    }

    private fun getProfile() {

        if (Utility.isOnline(mActivity)) {

            Utility.showProgressDialog(mActivity)
            profileViewModel.getProfile(mActivity)!!
                .observe(viewLifecycleOwner, Observer { profileResponseData ->

                    Log.e("isGetProfileShown ", "isGetProfileShown " + isGetProfileShown);
                    if (!isGetProfileShown) {


                        if (profileResponseData?.data?.size!! > 0) {

                            Utility.hideProgressDialog(mActivity)
                            //UODATE UI
                            if (profileResponseData.data[0].birth_date != null) {

                                profileViewModel.age = profileResponseData.data[0].birth_date

                            }
                            profileViewModel.username = profileResponseData.data[0].name
                            profileViewModel.bio = profileResponseData.data[0].bio
                            profileViewModel.companyName = profileResponseData.data[0].company_name
                            profileViewModel.emailId = profileResponseData.data[0].email_id

                            emailEditText?.setText(profileResponseData.data[0].email_id)
                            usernameEditText?.setText(profileResponseData.data[0].name)
                            companyNameEditText?.setText(profileResponseData.data[0].company_name)
                            bioEditText?.setText(profileResponseData.data[0].bio)
                            designationEditText?.setText(profileResponseData.data[0].designation)
                            age_id?.setText(profileResponseData.data[0].birth_date)

                            if (profileResponseData.data[0].gender.equals("M")) {

                                gender_id?.setText("Male")

                            } else if (profileResponseData.data[0].gender.equals("F")) {

                                gender_id?.setText("Female")

                            } else {

                                gender_id?.setText("Other")

                            }
                            gender_id?.setText(profileResponseData.data[0].gender)
                            getUpcomingActivities()


                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + profileResponseData.message,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(mActivity)

                        }

                        isGetProfileShown = true

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


    private fun updateProfile() {
        if (Utility.isOnline(mActivity)) {

            profileViewModel.updateProfile(mActivity)!!
                .observe(viewLifecycleOwner, Observer { profileUpdateResponseData ->
                    Utility.hideProgressDialog(mActivity)

                    if (!isUpdateProfileShown) {

                        if (profileUpdateResponseData?.status.equals("success")) {

                            Utility.showDialog(
                                mActivity,
                                "Success",
                                "Your profile have been updated successfully.",
                                "Ok",
                                "Cancel"
                            )
                            disableField()

                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + profileUpdateResponseData?.message,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(mActivity)

                        }
                        isUpdateProfileShown = true
                    }

                })

        } else {
            Utility.hideProgressDialog(mActivity)

            Utility.showDialog(
                mActivity,
                "Network Error !!",
                "Please check your network connection.",
                "Ok",
                "Cancel"
            )
        }
    }

    private fun getUpcomingActivities() {

        if (Utility.isOnline(mActivity)) {

            Utility.showProgressDialog(mActivity)
            Log.e("Here 1", "Here 1")

            profileViewModel.getUpcomingActivties(mActivity)!!
                .observe(viewLifecycleOwner, Observer { upcomingActivitiesResponseData ->

                    if (!isGetUpcomingActivitiesShown) {

                        if (upcomingActivitiesResponseData?.data?.size!! > 0) {

                            Utility.hideProgressDialog(mActivity)

                            profileUpcomingEventRecyclerView =
                                mActivity.findViewById(R.id.profile_upcomming_event_rv)
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
                                    upcomingActivitiesResponseData.data,
                                    mActivity,
                                    object : ProfileUpcommingEventsAdapter.OnItemClickListener {
                                        override fun onItemClick() {



                                        }
/*
                                        val action =
                                            HomeFragmentDirections.actionHomeFragmentToCharityDeatilsFragment("from home fragment")
                                        navController.navigate(action)*/

                                    })
                            )

                            getCompletedActivities()

                        } else {

                            Utility.hideProgressDialog(mActivity)

                            getCompletedActivities()

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


    private fun getCompletedActivities() {

        if (Utility.isOnline(mActivity)) {

            Utility.showProgressDialog(mActivity)
            profileViewModel.getCompletedActivities(mActivity)!!
                .observe(viewLifecycleOwner, Observer { completedActivitiesResponseData ->

                    Utility.hideProgressDialog(mActivity)

                    if (!isGetPastActivitiesShown) {

                        if (completedActivitiesResponseData?.data?.size!! > 0) {


                            profileCompleteEventRecyclerView =
                                mActivity.findViewById(R.id.profile_complete_event_rv)
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
                                    mActivity,
                                    object : CompletedActivitiesAdapter.OnItemClickListener {
                                        override fun onItemClick() {


                                        }
                                    })
                            )

                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + completedActivitiesResponseData.message,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(mActivity)

                        }
                        isGetPastActivitiesShown = true
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

    private fun enableField() {

        bioEditText!!.setEnabled(true);
        bioEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        usernameEditText!!.setEnabled(true);
        usernameEditText!!.setBackgroundColor(resources.getColor(R.color.white))

        companyNameEditText!!.setEnabled(true);
        companyNameEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        designationEditText!!.setEnabled(true);
        designationEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        emailEditText!!.setEnabled(true);
        emailEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        ageSpinner!!.visibility = View.VISIBLE
        genderSpinner!!.visibility = View.VISIBLE

        ageSpinner!!.setEnabled(true);
        genderSpinner!!.setEnabled(true);

        age_id!!.visibility = View.GONE
        gender_id!!.visibility = View.GONE

    }

    private fun disableField() {

        bioEditText!!.setEnabled(false);
        bioEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        usernameEditText!!.setEnabled(false);
        usernameEditText!!.setBackgroundColor(resources.getColor(R.color.white))

        companyNameEditText!!.setEnabled(false);
        companyNameEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        emailEditText!!.setEnabled(false);
        designationEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        designationEditText!!.setEnabled(false);
        emailEditText!!.setBackgroundResource(R.drawable.grey_round_corner_bg);

        ageSpinner!!.visibility = View.VISIBLE
        genderSpinner!!.visibility = View.VISIBLE

        ageSpinner!!.setEnabled(false);
        genderSpinner!!.setEnabled(false);

    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun init() {

        MainActivity.bottomNavigationView.getMenu().setGroupCheckable(0, false, true);

        MainActivity.screenName.text = "Edit Profile"

        usernameEditText = mActivity.findViewById(R.id.user_name_tv)
        bioEditText = mActivity.findViewById(R.id.bio_et)
        companyNameEditText = mActivity.findViewById(R.id.company_id)
        emailEditText = mActivity.findViewById(R.id.email_tv)
        designationEditText = mActivity.findViewById(R.id.designation_id)

        editImageView = mActivity.findViewById(R.id.edit_img)
        ageSpinner = mActivity.findViewById(R.id.age_sp)
        genderSpinner = mActivity.findViewById(R.id.gender_sp)

        ageSpinner?.setOnItemSelectedListener(this);
        genderSpinner?.setOnItemSelectedListener(this);


        val adapt: ArrayAdapter<*> =
            ArrayAdapter(mActivity, R.layout.spinner_item, profileViewModel.ageList)
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner!!.setAdapter(adapt)

        val adap1: ArrayAdapter<*> =
            ArrayAdapter(mActivity, R.layout.spinner_item, profileViewModel.genderList)
        adap1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner!!.setAdapter(adap1)


        Utility.setSemibold(mActivity, usernameEditText!!)
        Utility.setSemibold(mActivity, bio_title!!)
        Utility.setSemibold(mActivity, age_t_id!!)
        Utility.setSemibold(mActivity, company_t_id!!)
        Utility.setSemibold(mActivity, gender_t_id!!)
        Utility.setSemibold(mActivity, designation_t_id!!)
        Utility.setSemibold(mActivity, email_t_tv!!)
        Utility.setSemibold(mActivity, event_tv!!)
        Utility.setSemibold(mActivity, complete_tv!!)

        Utility.setSolidFontAwesome(mActivity, camara_img!!)
        camara_img.bringToFront()

        Utility.setSolidFontAwesome(mActivity, edit_img!!)

        editImageView!!.setOnClickListener {

            if (edit) {

                editImageView!!.text = "Save"
                /*editImageView!!.setBackgroundResource(R.drawable.blue_circle);
                editImageView!!.setImageDrawable(mActivity?.getDrawable(R.drawable.save))*/
                enableField()
                edit = false

            } else {

                editImageView!!.text = "Edit"
                compressedImageFile = Compressor(mActivity).compressToFile(finalFile)

                val reqFile: RequestBody =  RequestBody.create("image/*".toMediaTypeOrNull(), compressedImageFile)
                val body: MultipartBody.Part =  createFormData("file", compressedImageFile.getName(), reqFile)
                val userProfileUploadImageRequestModel = UserProfileUploadImageRequestModel(body)
                Utility.showProgressDialog(mActivity)
                uploadImage(userProfileUploadImageRequestModel)

            }
        }


        camara_img.setOnClickListener {

            chooseImageDialog("Choose Image","Please Select One Option",mActivity,true)

        }

        disableField()
        getProfile();
    }


    fun chooseImageDialog(
        title: String?, message: String?,
        context: Context?, redirectToPreviousScreen: Boolean
    ) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Gallery",
            DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, SELECT_PICTURE)
            })
        alertDialog.setNegativeButton("Camera",
            DialogInterface.OnClickListener { dialog, which ->
                val intentfile = Intent(
                    "android.media.action.IMAGE_CAPTURE"
                )
                startActivityForResult(intentfile, CAPTURE_PICTURE)
                dialog.dismiss()
            })
        alertDialog.show()
    }


    private fun uploadImage(userProfileUploadImageRequestModel: UserProfileUploadImageRequestModel) {

        if (Utility.isOnline(mActivity)) {

            profileViewModel.uploadImage(mActivity,userProfileUploadImageRequestModel)!!
                .observe(viewLifecycleOwner, Observer { profileUpdateResponseData ->

                    if (!isUpdateProfileShown) {

                        if (profileUpdateResponseData?.status.equals("success")) {

                            updateProfile()

                        } else {

                            Utility.showDialog(
                                mActivity,
                                "Error !!",
                                "" + profileUpdateResponseData?.status,
                                "Ok",
                                "Cancel"
                            )
                            Utility.hideProgressDialog(mActivity)

                        }
                        isUpdateProfileShown = true
                    }

                })

        } else {

            Utility.hideProgressDialog(mActivity)

            Utility.showDialog(
                mActivity,
                "Network Error !!",
                "Please check your network connection.",
                "Ok",
                "Cancel"
            )
        }


    }


    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data);

        Log.e("resultCode","resultCode "+resultCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {


                val selectedImage1: Uri = data?.getData()!!

                var myBitmap: Bitmap? =  null
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(
                        mActivity.getContentResolver(),
                        selectedImage1
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                user_image.setImageBitmap(myBitmap)
                finalFile = File(getRealPathFromURI(selectedImage1))
                Log.e("data","data "+data);
                Log.e("finalFile","finalFile "+finalFile);



            }else if (requestCode == CAPTURE_PICTURE) {

                //val selectedImage1: Uri = data?.getData()!!
                var myBitmap: Bitmap? =  null
                myBitmap = data?.getExtras()?.get("data") as Bitmap?
                user_image.setImageBitmap(myBitmap)
                finalFile = File(getRealPathFromURI(getImageUri(mActivity, myBitmap!!)!!))
                Log.e("data","data "+data);
                Log.e("finalFile","finalFile "+finalFile);
            }
        }
    }


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
    fun getRealPathFromURI(uri: Uri): String? {
        val cursor: Cursor? =
            mActivity.getContentResolver().query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val idx = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return idx?.let { cursor?.getString(it) }
    }
    /**
     * helper to retrieve the path of an image URI
     */
    fun getPath(uri: Uri?): String? {
        // just some safety built in
        Log.e("uri ","uri "+uri);
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = mActivity!!.managedQuery(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val path = cursor.getString(column_index)
            cursor.close()
            return path
        }
        // this is our fallback here
        return uri.path
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {


    }


}