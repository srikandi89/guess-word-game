package com.babbel.guesswordgame.screen.game

import com.babbel.guesswordgame.datasource.dto.WordPair
import com.babbel.guesswordgame.interactor.Interactor

class GameViewModel(private val interactor: Interactor) {
    private val words = mutableListOf<WordPair>()

    fun copyWords(source: List<WordPair>) {
        words.addAll(source)
    }
}