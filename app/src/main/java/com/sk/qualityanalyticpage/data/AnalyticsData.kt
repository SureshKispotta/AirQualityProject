package com.sk.qualityanalyticpage.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class AnalyticsDat (@SerializedName("city")
                         var city : String ?= null ,
                         @SerializedName("aqi")
                         var value : Double ?= null ) {
    val updateTime = Date()

    override fun equals(other: Any?): Boolean {
       return when(other) {
            is AnalyticsDat -> {city.equals(other.city)}
            else -> false
        }
    }
}