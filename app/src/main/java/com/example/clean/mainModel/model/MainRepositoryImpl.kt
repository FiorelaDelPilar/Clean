package com.example.clean.mainModel.model

import com.example.clean.common.EventBus
import com.example.clean.common.SportEvent
import com.example.clean.common.getAdEventsInRealtime
import com.example.clean.common.getResultEventsInRealtime
import com.example.clean.common.someTime
import kotlinx.coroutines.delay

class MainRepositoryImpl {
    suspend fun getEvents() {
        val events = getResultEventsInRealtime()
        events.forEach { event ->
            delay(someTime())
            publishEvent(event)
        }
    }

    suspend fun saveResult(result: SportEvent.ResultSuccess) {
        val response = if (result.isWarning)
            SportEvent.ResultError(30, "Error al guardar.")
        else SportEvent.SaveEvent
        publishEvent(response)
    }

    suspend fun registerAd() {
        val events = getAdEventsInRealtime()
        publishEvent(events.first())
    }

    suspend fun closeAd() {
        publishEvent(SportEvent.ClosedAdEvent)
    }

    private suspend fun publishEvent(event: SportEvent) {
        EventBus.instance().publish(event)
    }
}