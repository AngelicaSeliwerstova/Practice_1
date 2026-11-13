package com.example.vkcourse.applist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState
    data object Installed : InstallState
}

sealed interface AppDetailsState {
    data class Content(val installState: InstallState = InstallState.NotInstalled) : AppDetailsState
}

class AppDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Content())
    val state = _state.asStateFlow()

    private var installJob: Job? = null

    fun onInstallClick() {
        val current = _state.value as? AppDetailsState.Content ?: return
        when (current.installState) {
            is InstallState.Installing -> {
                installJob?.cancel()
                installJob = null
                _state.update { AppDetailsState.Content(InstallState.NotInstalled) }
            }
            InstallState.NotInstalled -> {
                _state.update { AppDetailsState.Content(InstallState.Installing(0)) }
                installJob = viewModelScope.launch { emulateInstall() }
            }
            InstallState.Installed -> Unit
        }
    }

    private suspend fun emulateInstall() {
        for (progress in 5..100 step 5) {
            delay(150)
            _state.update { state ->
                (state as AppDetailsState.Content).copy(
                    installState = InstallState.Installing(progress)
                )
            }
        }
        _state.update { state ->
            (state as AppDetailsState.Content).copy(installState = InstallState.Installed)
        }
    }
}
