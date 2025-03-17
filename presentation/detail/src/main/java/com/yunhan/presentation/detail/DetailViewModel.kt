package com.yunhan.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yunhan.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
) : BaseViewModel<DetailState, DetailSideEffect, DetailAction>(DetailState.init) {

    override fun onAction(action: DetailAction) {
        when(action) {
            else -> Unit
        }
    }

    init {
        viewModelScope.fetch(
            onInit = {
                delay(1000)
            },
            onLoading = {
                delay(1000)
            },
            onSuccess = {
                reduce(currentState.copy(text = stateHandle.get<String>("from") ?: "error"))
            }
        )
    }
}