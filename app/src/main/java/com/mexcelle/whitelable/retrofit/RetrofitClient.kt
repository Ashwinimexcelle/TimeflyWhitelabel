package com.mexcelle.whitelable.retrofit

import android.os.StrictMode
import com.mexcelle.whitelable.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor




object RetrofitClient {

    const val MainServer = Constants.BASE_URL

    val retrofitClient: Retrofit.Builder by lazy {

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy { retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }
}
