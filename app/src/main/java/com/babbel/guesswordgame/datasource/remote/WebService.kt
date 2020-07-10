package com.babbel.guesswordgame.datasource.remote

import com.babbel.guesswordgame.datasource.remote.model.EngSpaPair
import io.reactivex.Single
import retrofit2.http.GET

interface WebService {
    @GET("/DroidCoder/7ac6cdb4bf5e032f4c737aaafe659b33/raw/baa9fe0d586082d85db71f346e2b039c580c5804/words.json")
    fun getEngSpaPairs(): Single<List<EngSpaPair>>
}