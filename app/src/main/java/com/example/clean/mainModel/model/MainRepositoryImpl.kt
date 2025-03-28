package com.example.clean.mainModel.model

import com.example.clean.common.EventBus
import com.example.clean.common.SportEvent
import com.example.clean.common.getAdEventsInRealtime
import com.example.clean.common.getResultEventsInRealtime
import com.example.clean.common.someTime
import kotlinx.coroutines.delay

class MainRepositoryImpl(private val ds: DataSource) : MainRepository {
    override suspend fun getEvents() {
        val events = ds.getAllEvents()
        events.forEach { event ->
            delay(someTime())
            publishEvent(event)
        }
    }

    override suspend fun saveResult(result: SportEvent.ResultSuccess) {
        /*val response = if (result.isWarning)
            SportEvent.ResultError(30, "Error al guardar.")
        else SportEvent.SaveEvent
         */
        val response = ds.save(result)
        publishEvent(response)

    }

    override suspend fun registerAd() {
        //val events = getAdEventsInRealtime().first()
        val event = ds.registerAd()
        publishEvent(event)
    }

    /*override suspend fun closeAd() {
        publishEvent(SportEvent.ClosedAdEvent)
    }*/

    private suspend fun publishEvent(event: SportEvent) {
        EventBus.instance().publish(event)
    }
}