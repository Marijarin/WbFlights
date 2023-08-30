package com.marijarin.timetotravel.repository

import com.marijarin.timetotravel.entity.FlightEntity
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    val flights: Flow<List<FlightEntity>>
    suspend fun getFlights(){}
    suspend fun likeByToken(token:String){}
}