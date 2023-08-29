package com.marijarin.timetotravel.repository

import com.marijarin.timetotravel.dto.Flight
import com.marijarin.timetotravel.entity.FlightEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface FlightRepository {
    val flights: Flow<List<FlightEntity>>
    suspend fun getFlights(){}
    suspend fun likeByToken(token:String){}
}