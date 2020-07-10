package com.babbel.guesswordgame.interactor

import com.babbel.guesswordgame.datasource.dto.WordPair
import io.reactivex.Observable

interface Interactor {
    fun getOptionObservable(): Observable<WordPair>

    fun getQuestionObservable(): Observable<WordPair>
}