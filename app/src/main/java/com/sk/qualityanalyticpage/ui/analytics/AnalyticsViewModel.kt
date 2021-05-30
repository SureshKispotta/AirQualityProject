package com.sk.qualityanalyticpage.ui.analytics

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.sk.qualityanalyticpage.data.AnalyticsRepository

class AnalyticsViewModel (private val repository: AnalyticsRepository) : ViewModel(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startListening() = repository.startListening()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopListening() = repository.stopListing()

    fun getConnectionStatus() = repository.getConnectionStatus()

    fun getAnalyticsDetails() = repository.getAnalyticsDetails()
}