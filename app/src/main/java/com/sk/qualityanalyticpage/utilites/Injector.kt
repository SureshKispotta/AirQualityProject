package com.sk.qualityanalyticpage.utilites

import com.sk.qualityanalyticpage.data.AnalyticsRepository
import com.sk.qualityanalyticpage.data.remote.SocketService
import com.sk.qualityanalyticpage.ui.analytics.AnalyticsViewModelFactory

object Injector {
    fun provideAnalyticsViewModelFactory() : AnalyticsViewModelFactory {
        val repository = AnalyticsRepository.getInstance(SocketService.getInstance())
        return AnalyticsViewModelFactory(repository)
    }


}