package com.babbel.guesswordgame.datasource.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EngSpaPair(
    @SerializedName("text_eng")
    val textEng: String,

    @SerializedName("text_spa")
    val textSpa: String
): Parcelable