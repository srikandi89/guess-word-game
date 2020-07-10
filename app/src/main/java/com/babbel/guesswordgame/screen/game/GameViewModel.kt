package com.babbel.guesswordgame.screen.game

import com.babbel.guesswordgame.datasource.dto.WordPair
import com.babbel.guesswordgame.interactor.Interactor

class GameViewModel(private val interactor: Interactor) {
    private var scores: Int = 0
    private val words = mutableListOf<WordPair>()
    private val options = mutableListOf<WordPair>()
    private var questionAnswer = WordPair()
    private var displayedOption = WordPair()

    fun getOptionObservable() = interactor.getOptionObservable()

    fun getQuestionObservable() = interactor.getQuestionObservable()

    fun getScoreObservable() = interactor.getScoreObservable()

    private fun increaseScore() {
        scores++
        interactor.emitScore(scores)
    }

    fun copyWords(source: List<WordPair>) {
        words.addAll(source)
    }

    private fun nextOption() {
        if (options.isNotEmpty()) {
            displayedOption = options.removeAt((0 until options.size).random())
            interactor.emitOption(displayedOption)
        }
        else {
            nextQuestion()
        }
    }

    fun nextQuestion() {
        options.clear()
        questionAnswer = randomWord()

        // add another word to be randomized as answer candidate
        options.add(questionAnswer)
        while (options.size < MAX_OPTIONS) {
            val option = randomWord()

            if (option != options[0]) {
                options.add(option)
            }
        }

        interactor.emitQuestion(questionAnswer)
        nextOption()
    }

    private fun randomWord(): WordPair = words[(0 until words.size).random()]

    fun submitAnswer(answer: Boolean) {
        val isTranslationCorrect = (displayedOption.lang == questionAnswer.lang)
        val isOptionNotEmpty = displayedOption.lang.isNotEmpty()
        val correct = (isTranslationCorrect && isOptionNotEmpty) == answer

        if (correct) {
            increaseScore()
        }

        nextQuestion()
    }

    fun getOptionList(): List<WordPair> {
        return options
    }

    companion object {
        const val MAX_OPTIONS = 2
    }
}