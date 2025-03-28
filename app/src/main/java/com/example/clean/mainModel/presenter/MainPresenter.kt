package com.example.clean.mainModel.presenter

import com.example.clean.common.SportEvent

interface MainPresenter {
    fun onCreate()
    fun onDestroy()
    suspend fun refresh()
    suspend fun getEvents()
    suspend fun registerAd()
    suspend fun closeAd()
    suspend fun saveResult(result: SportEvent.ResultSuccess)
}