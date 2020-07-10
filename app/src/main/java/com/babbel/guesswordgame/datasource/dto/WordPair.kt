package com.babbel.guesswordgame.datasource.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordPair(
    val lang: String = "",
    val translation: String = ""
): Parcelable