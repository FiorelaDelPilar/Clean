package com.example.clean.mainModel.model

interface MainRepository {
    suspend fun getEvents()
    suspend fun saveResult()
    suspend fun registerAd()
    suspend fun closeAd()
}