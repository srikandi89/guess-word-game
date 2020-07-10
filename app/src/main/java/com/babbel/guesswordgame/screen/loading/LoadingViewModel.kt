package com.babbel.guesswordgame.screen.loading

import com.babbel.guesswordgame.datasource.dto.WordPair
import com.babbel.guesswordgame.datasource.remote.RemoteRepository
import io.reactivex.Single

class LoadingViewModel(private val remoteRepository: RemoteRepository) {

    fun getEngSpaPairs(): Single<List<WordPair>> {
        return remoteRepository.getEngSpaPairs()
    }
}