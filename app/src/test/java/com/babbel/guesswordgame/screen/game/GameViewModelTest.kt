package com.babbel.guesswordgame.screen.game

import com.babbel.guesswordgame.datasource.dto.WordPair
import com.babbel.guesswordgame.interactor.InteractorImpl
import io.reactivex.android.plugins.RxAndroidPlugins.setInitMainThreadSchedulerHandler
import io.reactivex.android.plugins.RxAndroidPlugins.setMainThreadSchedulerHandler
import io.reactivex.plugins.RxJavaPlugins.setIoSchedulerHandler
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameViewModelTest {
    lateinit var SUT: GameViewModel

    private val fakeWordPairs = provideWords()

    private val interactor = InteractorImpl()

    @Before
    fun setUp() {
        setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        setMainThreadSchedulerHandler { Schedulers.trampoline() }
        setIoSchedulerHandler { Schedulers.trampoline() }
        SUT = GameViewModel(interactor)
        SUT.copyWords(fakeWordPairs)
    }

    @Test
    fun `trigger next question will generate options candidate to be randomized`() {
        SUT.nextQuestion()

        assertTrue(SUT.getOptionList().isNotEmpty())
    }

    @Test
    fun `trigger next question will generate one option`() {
        SUT.nextQuestion()

        assertEquals(1, SUT.getOptionList().size)
    }

    @Test
    fun `trigger next question, question emitter will emit one question`() {
        val values = SUT.getQuestionObservable()
            .test()
            .values()

        SUT.nextQuestion()

        assertEquals(1, values.size)
    }

    @Test
    fun `submit answer, user choose correct, score increased`() {
        val testObserver = SUT.getScoreObservable().test()
        SUT.copyWords(provideEqualWords())
        SUT.nextQuestion()
        SUT.submitAnswer(true)

        val scores = testObserver.values()

        assertEquals(1, scores.size)
        assertEquals(1, scores[0])
    }


    private fun provideWords(): List<WordPair> {
        return mutableListOf(
            WordPair("a", "a1"),
            WordPair("b", "b1"),
            WordPair("c", "c1"),
            WordPair("d", "d1"),
            WordPair("e", "e1")
        )
    }

    private fun provideEqualWords(): List<WordPair> {
        return mutableListOf(
            WordPair("a", "a1"),
            WordPair("a", "a1"),
            WordPair("a", "a1"),
            WordPair("a", "a1"),
            WordPair("a", "a1")
        )
    }

    @After
    fun tearDown() {
        setInitMainThreadSchedulerHandler(null)
        setMainThreadSchedulerHandler(null)
    }
}