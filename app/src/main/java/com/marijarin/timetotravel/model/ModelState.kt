package com.marijarin.timetotravel.model

sealed interface ModelState {
    object Loading: ModelState
    object Error: ModelState
    object Success: ModelState
    object Empty: ModelState
}