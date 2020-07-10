package com.babbel.guesswordgame.datasource.remote

import android.content.Context
import com.babbel.guesswordgame.R
import com.babbel.guesswordgame.datasource.dto.WordPair
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRepository(context: Context) {
    private val baseUrl: String = context.getString(R.string.base_url)

    private fun getServiceInterface(): WebService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(WebService::class.java)

    fun getEngSpaPairs(): Single<List<WordPair>> {
        return getServiceInterface().getEngSpaPairs().map { pairs ->
            return@map pairs.map { pair ->
                WordPair(pair.textEng, pair.textSpa)
            }
        }
    }
}