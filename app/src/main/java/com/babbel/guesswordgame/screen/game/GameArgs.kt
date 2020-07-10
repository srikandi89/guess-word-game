package com.babbel.guesswordgame.screen.game

import android.os.Parcelable
import com.babbel.guesswordgame.datasource.dto.WordPair
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameArgs(
    val engSpaPairs: List<WordPair> = listOf()
): Parcelable