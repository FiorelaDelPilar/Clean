package com.example.clean.mainModel.model

import com.example.clean.common.SportEvent

interface DataSource {
    fun save(result: SportEvent.ResultSuccess): SportEvent
    fun getAllEvents(): List<SportEvent>
    fun registerAd(): SportEvent.AdEvent
}