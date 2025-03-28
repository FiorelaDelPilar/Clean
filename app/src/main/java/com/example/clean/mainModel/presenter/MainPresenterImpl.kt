package com.example.clean.mainModel.presenter

import android.util.Log
import com.example.clean.common.EventBus
import com.example.clean.common.SportEvent
import com.example.clean.mainModel.model.MainRepositoryImpl
import com.example.clean.mainModel.view.MainActivity
import com.example.clean.mainModel.view.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainPresenterImpl(
    private val view: MainView, private val repository: MainRepositoryImpl
) :
    MainPresenter {
    private lateinit var viewScope: CoroutineScope

    override fun onCreate() {
        viewScope = CoroutineScope(Dispatchers.IO + Job())
        onEvent()
    }

    override suspend fun refresh() {
        view.clearAdapter()
        getEvents()
        view.showAdUI(true)
    }

    override suspend fun getEvents() {
        view.showProgress(true)
        repository.getEvents()
    }

    override suspend fun registerAd() {
        repository.registerAd()
    }

    override suspend fun closeAd() {
        view.showAdUI(false)
        Log.i("DEV:::", "Ad was closed. Send data to server")
    }

    override suspend fun saveResult(result: SportEvent.ResultSuccess) {
        //view.showProgress(true) => Curiosamente esto genera un punto blanco veloz al dar click a los items si es que se descomenta
        repository.saveResult(result)
    }

    override fun onDestroy() {
        viewScope.cancel()
    }

    private fun onEvent() {
        viewScope.launch {
            EventBus.instance().subscribe<SportEvent> { event ->
                this.launch {
                    when (event) {
                        is SportEvent.ResultSuccess -> {
                            view.add(event)
                            view.showProgress(false)
                        }

                        is SportEvent.ResultError -> {
                            view.showSnackbar("Code ${event.code}, Message: ${event.msg} ")
                            view.showProgress(false)
                        }

                        is SportEvent.AdEvent -> {
                            view.showToast("Ad click. Send data to server---")
                        }

                        is SportEvent.SaveEvent -> {
                            view.showToast("Guardado exitosamente")
                            view.showProgress(false)
                        }
                    }
                }
            }
        }
    }
}