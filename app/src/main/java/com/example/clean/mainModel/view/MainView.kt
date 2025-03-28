package com.example.clean.mainModel.view

import com.example.clean.common.SportEvent

interface MainView {
    fun add(event: SportEvent.ResultSuccess)
    fun clearAdapter()
    suspend fun showAdUI(isVisible: Boolean)
    fun showProgress(isVisible: Boolean)
    suspend fun showToast(msg: String)
    fun showSnackbar(msg: String)
}