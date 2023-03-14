package com.github.donghune.data.remote.network

import com.github.donghune.data.remote.response.MananaSongResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MananaKaraokeService {

    enum class Period(val value: String) {
        DAILY("daily"), WEEKLY("weekly"), MONTHLY("monthly"),
    }

    @GET("karaoke/song/{title}/{brand}.json")
    suspend fun searchByTitle(
        @Path("brand") brand: String = "tj",
        @Path("title") title: String
    ): List<MananaSongResponse>

    @GET("karaoke/singer/{singer}/{brand}.json")
    suspend fun searchBySinger(
        @Path("brand") brand: String = "tj",
        @Path("singer") singer: String
    ): List<MananaSongResponse>

    @GET("karaoke/no/{no}/{brand}.json")
    suspend fun searchByNo(
        @Path("brand") brand: String = "tj",
        @Path("no") no: String
    ): List<MananaSongResponse>

    @GET("karaoke/popular/{brand}/{period}.json")
    suspend fun getPopularSongs(
        @Path("brand") brand: String = "tj", // kumyoung
        @Path("period") period: String // daily / weekly / monthly
    ): List<MananaSongResponse>

    @GET("karaoke/release/{release}/{brand}.json")
    suspend fun getReleaseSongs(
        @Path("brand") brand: String = "tj", // kumyoung
        @Path("release") release: String // 202301 / 20230126
    ): List<MananaSongResponse>
}
