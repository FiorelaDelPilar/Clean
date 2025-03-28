package com.example.clean.mainModel.model

import com.example.clean.common.SportEvent

interface MainRepository {
    suspend fun getEvents()
    suspend fun saveResult(result: SportEvent.ResultSuccess)
    suspend fun registerAd()
    //suspend fun closeAd()
}