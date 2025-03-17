package com.yunhan.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<NavigationState> = MutableStateFlow(NavigationState())
    val state = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<NavigationSideEffect> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onAction(action: NavigationAction) {
        when(action) {
            is NavigationAction.StartDetailActivity -> {
                startDetailActivity(action.sampleNavType)
            }
            else -> Unit
        }
    }

    private fun startDetailActivity(sampleNavType: SampleNavType) {
        viewModelScope.launch {
            _sideEffect.emit(NavigationSideEffect.StartDetailActivity(sampleNavType))
        }
    }

    fun fetch(sampleNavType: SampleNavType) {
        viewModelScope.launch {
            _state.emit(NavigationState(sampleNavType))
        }
    }
}