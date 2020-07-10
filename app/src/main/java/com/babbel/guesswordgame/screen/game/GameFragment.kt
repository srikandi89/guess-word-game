package com.babbel.guesswordgame.screen.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.babbel.guesswordgame.R
import com.babbel.guesswordgame.interactor.InteractorImpl
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment(R.layout.fragment_game) {
    private val args: GameFragmentArgs by navArgs()
    private val engSpaPairs by lazy {
        args.gameArgs?.engSpaPairs
    }

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = GameViewModel(InteractorImpl())
        viewModel.copyWords(engSpaPairs.orEmpty())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setGameButtonState(GameButtonState.START)

        button_start.setOnClickListener {
            setGameButtonState(GameButtonState.PLAY)
        }
    }

    private fun setGameButtonState(state: Int) {
        viewflipper_button.displayedChild = state
    }

    object GameButtonState {
        const val START = 0
        const val PLAY = 1
    }
}