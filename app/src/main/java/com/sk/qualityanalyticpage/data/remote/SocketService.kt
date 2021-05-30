package com.sk.qualityanalyticpage.data.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sk.qualityanalyticpage.data.AnalyticsDat
import com.sk.qualityanalyticpage.utilites.BaseObservable
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.net.URISyntaxException

class SocketService private constructor(): BaseObservable<EventListener>() {
    private val TAG = SocketService::class.java.simpleName
    private val SOCKET_URL = "ws://city-ws.herokuapp.com/"
    private lateinit var webSocketClient: WebSocketClient
    private var gson = Gson()

    init {
        val coinbaseUri: URI? = URI(SOCKET_URL)
        createWebSocketClient(coinbaseUri)
    }

    companion object {
        @Volatile private var instance : SocketService? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance?: SocketService().also { instance = it }
        }
    }

    @Throws(URISyntaxException::class)
    fun startListening() {
        if (!webSocketClient.isOpen) {
            val coinbaseUri: URI? = URI(SOCKET_URL)
            createWebSocketClient(coinbaseUri)
            webSocketClient.connect()
        }
    }

    private fun createWebSocketClient(coinbaseUri: URI?) {
        webSocketClient = object : WebSocketClient(coinbaseUri) {

            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen Connection..")
                notifyConnect()
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "Message $message")
                val messageType = object : TypeToken<List<AnalyticsDat>>() {}.type
                var dataList : List<AnalyticsDat> = gson.fromJson(message, messageType)
                notifyData(dataList)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose")
                notifyConnectionClose()
            }

            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError: ${ex?.message}")
                notifyError()
            }
        }
    }

    private fun notifyConnect() {
        for (listener in getListeners()) listener.onConnect()
    }

    private fun notifyConnectionClose() {
        for (listener in getListeners()) listener.onDisconnect()
    }

    private fun notifyError() {
        for (listener in getListeners()) listener.onConnectionError()
    }

    private fun notifyData(data:List<AnalyticsDat>) {
        for (listener in getListeners()) listener.onNewMessage(data)
    }

    @Throws(InterruptedException::class)
    fun clearSession() {
        webSocketClient?.closeBlocking()
    }
}