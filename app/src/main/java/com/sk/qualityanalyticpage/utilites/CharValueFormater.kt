package com.sk.qualityanalyticpage.utilites

import android.util.Log
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class CharValueFormater : IndexAxisValueFormatter() {
    private var counter = 0;
    private val diff = 30;
    override fun getFormattedValue(value: Float): String {
        val label = value * diff
        counter += 1
        Log.d("Form", "Connected..$value")
        return "${label.toInt()}"
    }
}