package com.merp.jet.ig.downloader.network

import com.merp.jet.ig.downloader.model.ReelResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ReelApi {

    @FormUrlEncoded
    @POST("video-data")
    suspend fun getReelData(
        @Field("url") url: String,
    ): ReelResponse
}