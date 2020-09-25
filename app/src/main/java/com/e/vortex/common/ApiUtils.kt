package com.e.vortex.common

import com.e.vortex.BuildConfig
import com.e.vortex.data.api.VortexApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ApiUtils {

    val NETWORK_EXCEPTIONS: List<Class<*>> = listOf(
        UnknownHostException::class.java,
        SocketTimeoutException::class.java,
        ConnectException::class.java
    )

    private lateinit var client: OkHttpClient

    private lateinit var gson: Gson

    private lateinit var api: VortexApi

    private var retrofit: Retrofit? = null
        get() {
            if (!this::gson.isInitialized) {
                gson = Gson()
            }

            if (field == null) {
                field = Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .client(basicAuthClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }

            return field
        }

    private val basicAuthClient: OkHttpClient
        get() {
            if (!this::client.isInitialized) {
                val builder = OkHttpClient().newBuilder()
                client = builder.build()
            }

            return client
        }

    val apiService: VortexApi
        get() {
            if (!this::api.isInitialized) {
                api = retrofit!!.create(VortexApi::class.java)
            }

            return api
        }
}