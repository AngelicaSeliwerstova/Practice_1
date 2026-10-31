package com.example.vkcourse.applist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vkcourse.applist.data.DefaultCatalogRepository
import com.example.vkcourse.applist.domain.model.CatalogApp
import com.example.vkcourse.applist.domain.usecase.GetCatalogAppsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

sealed interface CatalogEvent {
    data class ShowMessage(val message: String) : CatalogEvent
}

class CatalogViewModel(
    getCatalogApps: GetCatalogAppsUseCase
) : ViewModel() {
    private val _apps = MutableStateFlow(getCatalogApps())
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (!modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
                    throw IllegalArgumentException("Неизвестный тип ViewModel: ${modelClass.name}")
                }

                val repository = DefaultCatalogRepository()
                val viewModel = CatalogViewModel(GetCatalogAppsUseCase(repository))
                return requireNotNull(modelClass.cast(viewModel))
            }
        }
    }
}
