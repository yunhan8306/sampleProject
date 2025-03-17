package com.yunhan.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunhan.presentation.detail.component.DetailSideEffect
import com.yunhan.presentation.detail.component.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state: MutableStateFlow<DetailState> = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<DetailSideEffect> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    private fun fetch() {
        viewModelScope.launch {
            _state.emit(DetailState(stateHandle.get<String>("from") ?: "error"))
        }
    }

    init {
        fetch()
    }
}