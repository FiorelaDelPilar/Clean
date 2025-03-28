package com.example.clean.mainModel.model

import com.example.clean.common.EventBus
import com.example.clean.common.SportEvent
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
        val response = ds.save(result)
        publishEvent(response)

    }

    override suspend fun registerAd() {
        val event = ds.registerAd()
        publishEvent(event)
    }

    private suspend fun publishEvent(event: SportEvent) {
        EventBus.instance().publish(event)
    }
}