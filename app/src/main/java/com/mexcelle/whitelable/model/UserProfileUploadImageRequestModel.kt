package com.mexcelle.whitelable.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody


data class UserProfileUploadImageRequestModel(


    val file: MultipartBody.Part,

   /* @SerializedName("file") @Expose
    private var file: MultipartBody.Part*/

)


