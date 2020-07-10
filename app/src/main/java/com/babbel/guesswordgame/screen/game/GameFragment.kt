package com.babbel.guesswordgame.screen.game

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.View
import android.view.Window
import android.view.animation.TranslateAnimation
import androidx.navigation.fragment.navArgs
import com.babbel.guesswordgame.R
import com.babbel.guesswordgame.interactor.InteractorImpl
import com.babbel.guesswordgame.utils.TimeBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment(R.layout.fragment_game) {
    private val args: GameFragmentArgs by navArgs()
    private val engSpaPairs by lazy {
        args.gameArgs?.engSpaPairs
    }

    private lateinit var viewModel: GameViewModel

    private val disposable = CompositeDisposable()

    private val isHaveActionBar by lazy {
        val id = resources.getIdentifier(
            CONFIG_SHOW_NAV_BAR,
            DEF_TYPE,
            DEF_PACKAGE
        )
        id > 0 && resources.getBoolean(id)
    }

    private val actionBarSize by lazy {
        if (isHaveActionBar) {
            val metrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
            val usableHeight = metrics.heightPixels
            requireActivity().windowManager.defaultDisplay.getRealMetrics(metrics)
            val realHeight = metrics.heightPixels
            if (realHeight > usableHeight) realHeight - usableHeight else 0
        } else 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = GameViewModel(InteractorImpl())
        viewModel.copyWords(engSpaPairs.orEmpty())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setGameButtonState(GameButtonState.START)

        val timeBarAnimation = TimeBar(timebar, TIME_BAR_FROM, TIME_BAR_TO)

        val disposableOptions = viewModel.getOptionObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                animateTimeBar(timeBarAnimation)
                animateFallingText(it.lang)
            }

        val disposableQuestion = viewModel
            .getQuestionObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showQuestion(it.translation)
            }

        button_start.setOnClickListener {
            setGameButtonState(GameButtonState.PLAY)
            viewModel.nextQuestion()
        }

        disposable.add(disposableOptions)
        disposable.add(disposableQuestion)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.clear()
    }

    private fun setGameButtonState(state: Int) {
        viewflipper_button.displayedChild = state
    }

    private fun showQuestion(question: String) {
        textview_question.text = question
    }

    private fun animateTimeBar(timeBarAnimation: TimeBar) {
        timeBarAnimation.duration = DURATION
        timebar.startAnimation(timeBarAnimation)
    }

    private fun getCanvasView(): View =
        requireActivity().window.findViewById(Window.ID_ANDROID_CONTENT)

    private fun getDisplayHeight() = getDisplayMetrics().heightPixels + actionBarSize

    private fun getDisplayMetrics(): DisplayMetrics {
        val dm = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dm)

        return dm
    }

    private fun animateFallingText(fallingText: String, fallingDuration: Long = DURATION) {
        with(textview_falling_text) {
            clearAnimation()
            text = fallingText
            y = 0F
        }

        val animation = TranslateAnimation(0f, 0F, 0f, getDisplayHeight().toFloat())
        animation.duration = fallingDuration

        val yTranslation = getCanvasView().height.toFloat() - textview_falling_text.height

        textview_falling_text.animate().withEndAction {
            Handler().post {
                viewModel.nextQuestion()
            }
        }.translationY(yTranslation).duration =
            fallingDuration

    }

    object GameButtonState {
        const val START = 0
        const val PLAY = 1
    }

    companion object {
        const val DURATION = 10000L
        const val CONFIG_SHOW_NAV_BAR = "config_showNavigationBar"
        const val DEF_TYPE = "bool"
        const val DEF_PACKAGE = "android"
        const val TIME_BAR_FROM = 0F
        const val TIME_BAR_TO = 100F
    }
}