package com.sk.qualityanalyticpage.utilites

import java.util.*
import java.util.concurrent.ConcurrentHashMap

open class BaseObservable<EventListener> {

    private val mListeners = Collections.newSetFromMap(
        ConcurrentHashMap<EventListener, Boolean>()
    )

    fun registerListener(listener: EventListener) {
        mListeners.add(listener)
    }

    fun unregisterListener(listener: EventListener) {
        mListeners.remove(listener)
    }

    protected fun getListeners(): Set<EventListener> {
        return Collections.unmodifiableSet(mListeners)
    }
}