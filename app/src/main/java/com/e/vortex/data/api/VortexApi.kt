package com.e.vortex.data.api

import com.e.vortex.model.Response
import io.reactivex.Single
import retrofit2.http.GET

interface VortexApi {

    @GET("/prod")
    fun getData(): Single<Response>
}