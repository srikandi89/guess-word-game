package com.babbel.guesswordgame.interactor

import com.babbel.guesswordgame.datasource.dto.WordPair
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class InteractorImpl: Interactor {
    private val optionEmitter = PublishSubject.create<WordPair>()
    private val questionEmitter = PublishSubject.create<WordPair>()

    override fun getOptionObservable(): Observable<WordPair> = optionEmitter

    override fun getQuestionObservable(): Observable<WordPair> = questionEmitter

    override fun emitOption(option: WordPair) {
        optionEmitter.onNext(option)
    }

    override fun emitQuestion(question: WordPair) {
        questionEmitter.onNext(question)
    }
}