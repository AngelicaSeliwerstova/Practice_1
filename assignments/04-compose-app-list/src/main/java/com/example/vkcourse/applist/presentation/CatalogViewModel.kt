package com.example.vkcourse.applist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vkcourse.applist.data.DefaultCatalogRepository
import com.example.vkcourse.applist.domain.model.CatalogApp
import com.example.vkcourse.applist.domain.usecase.GetCatalogAppsUseCase
import com.example.vkcourse.applist.domain.repository.CatalogRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

sealed interface CatalogEvent {
    data class ShowMessage(val message: String) : CatalogEvent
}

class CatalogViewModel(
    getCatalogApps: GetCatalogAppsUseCase,
    private val repository: CatalogRepository
) : ViewModel() {
    private val _apps = MutableStateFlow(getCatalogApps())
    val apps = _apps.asStateFlow()
    private val _ratings = MutableStateFlow<Map<String, Float>>(emptyMap())
    val ratings = _ratings.asStateFlow()
    private val _ratingTimes = MutableStateFlow<Pair<Long?, Long?>>(null to null)
    val ratingTimes = _ratingTimes.asStateFlow()

    private val eventChannel = Channel<CatalogEvent>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val ids = _apps.value.map(CatalogApp::id)
            val sequentialTime = measureTimeMillis {
                _ratings.value = repository.loadRatingsSequentially(ids)
            }
            _ratingTimes.value = sequentialTime to null
            val parallelTime = measureTimeMillis {
                _ratings.value = repository.loadRatingsInParallel(ids)
            }
            _ratingTimes.value = sequentialTime to parallelTime
        }
    }

    fun onToolbarClick() {
        showMessage("Каталог приложений готов")
    }

    fun appById(id: String): CatalogApp? = _apps.value.firstOrNull { it.id == id }

    fun onShareClick() {
        showMessage("Поделиться приложением")
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
                val viewModel = CatalogViewModel(GetCatalogAppsUseCase(repository), repository)
                return requireNotNull(modelClass.cast(viewModel))
            }
        }
    }
}
