package com.sk.qualityanalyticpage.data.remote

import com.sk.qualityanalyticpage.data.AnalyticsDat

interface EventListener {

    fun onConnect()

    fun onConnectionError()

    fun onDisconnect()

    fun onNewMessage(message: List<AnalyticsDat>)
}