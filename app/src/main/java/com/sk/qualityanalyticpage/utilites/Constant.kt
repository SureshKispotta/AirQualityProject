package com.sk.qualityanalyticpage.utilites

import com.sk.qualityanalyticpage.R
import android.content.Context
import androidx.core.content.ContextCompat

object Constant {
    fun getColorCode(context: Context, value: Int?) : Int {
        return when(value) {
            in 0..50 -> ContextCompat.getColor(context, R.color.good_air)
            in 51..100 -> ContextCompat.getColor(context, R.color.satisfactory_air)
            in 101..200 -> ContextCompat.getColor(context, R.color.moderate_air)
            in 201..300 -> ContextCompat.getColor(context, R.color.poor_air)
            in 301..400 -> ContextCompat.getColor(context, R.color.very_poor_air)
            else -> ContextCompat.getColor(context, R.color.sever_air)
        }
    }

    const val messageKey = "City"
}