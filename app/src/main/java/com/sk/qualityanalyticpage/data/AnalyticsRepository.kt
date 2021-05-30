package com.sk.qualityanalyticpage.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sk.qualityanalyticpage.data.remote.EventListener
import com.sk.qualityanalyticpage.data.remote.SocketService
import java.net.URISyntaxException

class AnalyticsRepository private constructor(var service: SocketService?) : EventListener {

    private val connectivityStatus = MutableLiveData<Int>()

    private val newMessages = MutableLiveData<List<AnalyticsDat>>()

    fun getConnectionStatus() = connectivityStatus as LiveData<Int>

    fun getAnalyticsDetails() = newMessages as LiveData<List<AnalyticsDat>>

    companion object {
        @Volatile private var instance : AnalyticsRepository? = null
        fun getInstance(service: SocketService) = instance ?: synchronized(this) {
            instance?: AnalyticsRepository(service).also { instance = it }
        }
    }

    fun startListening() {
        try {
            service!!.registerListener(this)
            service!!.startListening()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun stopListing() {
        service!!.unregisterListener(this)
        service!!.clearSession()
    }

    override fun onConnect() {
        connectivityStatus.postValue(0)
    }

    override fun onConnectionError() {
        connectivityStatus.postValue(2)
    }

    override fun onDisconnect() {
        connectivityStatus.postValue(1)
    }

    override fun onNewMessage(message: List<AnalyticsDat>) {
        newMessages.postValue(message)
    }
}