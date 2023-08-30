package com.marijarin.timetotravel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marijarin.timetotravel.dto.Flight
import com.marijarin.timetotravel.model.ModelState
import com.marijarin.timetotravel.repository.FlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val repository: FlightRepository
) : ViewModel() {
    val flights: Flow<List<Flight>> = repository.flights.map {
        it.map { entity->
            entity.toDto()
        }
    }.flowOn(Dispatchers.Default)
    private val _dataState = MutableLiveData<ModelState>(ModelState.Empty)
    val dataState: LiveData<ModelState>
        get() = _dataState
    init {
        loadFlights()
    }

    fun loadFlights() {
        viewModelScope.launch {
                try {
                    _dataState.value = ModelState.Loading
                    repository.getFlights()
                    _dataState.value = ModelState.Success
                } catch (e: Exception){
                    e.fillInStackTrace()
                    _dataState.value = ModelState.Error
                }
        }
    }
    fun likeByToken(token: String){
        viewModelScope.launch {
            repository.likeByToken(token)
        }
    }
}