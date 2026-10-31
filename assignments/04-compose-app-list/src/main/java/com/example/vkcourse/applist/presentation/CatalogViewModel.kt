package com.example.vkcourse.applist.presentation

import androidx.lifecycle.ViewModel
import com.example.vkcourse.applist.data.CatalogApp
import com.example.vkcourse.applist.data.sampleApps
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

sealed interface CatalogEvent {
    data class ShowMessage(val message: String) : CatalogEvent
}

class CatalogViewModel : ViewModel() {
    private val _apps = MutableStateFlow(sampleApps)
    val apps = _apps.asStateFlow()

    private val eventChannel = Channel<CatalogEvent>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    fun onToolbarClick() {
        showMessage("Каталог приложений готов")
    }

    fun appById(id: String): CatalogApp? = _apps.value.firstOrNull { it.id == id }

    fun onShareClick() {
        showMessage("Поделиться приложением")
    }

    fun onInstallClick() {
        showMessage("Установка появится позже")
    }

    private fun showMessage(message: String) {
        eventChannel.trySend(CatalogEvent.ShowMessage(message))
    }
}
