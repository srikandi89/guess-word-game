package com.babbel.guesswordgame.interactor

import com.babbel.guesswordgame.datasource.dto.WordPair
import io.reactivex.Observable

interface Interactor {
    fun getOptionObservable(): Observable<WordPair>

    fun getQuestionObservable(): Observable<WordPair>

    fun getScoreObservable(): Observable<Int>

    fun emitOption(option: WordPair)

    fun emitQuestion(question: WordPair)

    fun emitScore(score: Int)
}