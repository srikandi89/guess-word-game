package com.babbel.guesswordgame.utils

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar

class TimeBar(
    private val progressBar: ProgressBar,
    private val from: Float,
    private val to: Float
): Animation() {
    fun setDurationWithTimeUnit(duration: Long, timeUnit: Int = TimeUnit.MILIS) {
        setDuration(duration * timeUnit)
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)

        val timeValue = from + (to - from) * interpolatedTime
        progressBar.progress = timeValue.toInt()
    }

    object TimeUnit {
        const val SECOND = 1000
        const val MILIS = 1
    }
}