package com.sk.qualityanalyticpage.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sk.qualityanalyticpage.data.AnalyticsRepository

class AnalyticsViewModelFactory(private val repository: AnalyticsRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnalyticsViewModel(repository) as T
    }

}