package com.marijarin.timetotravel.repository

import com.google.gson.Gson
import com.marijarin.timetotravel.api.ApiService
import com.marijarin.timetotravel.dao.FlightDao
import com.marijarin.timetotravel.dto.Flights
import com.marijarin.timetotravel.entity.FlightEntity
import com.marijarin.timetotravel.entity.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class FlightRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: FlightDao
) : FlightRepository {
    override val flights: Flow<List<FlightEntity>> = dao.getAll()

    override suspend fun getFlights() {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getFlights()
                val fls = toFlightsFromJson(response).flights
                dao.insert(fls.toEntity())
            } catch (e: IOException) {
                throw e.fillInStackTrace()
            } catch (e: Exception) {
                throw e.fillInStackTrace()
            }
        }

    }

    private fun toFlightsFromJson(value: String): Flights {
        return Gson().fromJson(value, Flights::class.java)
    }

    override suspend fun likeByToken(token: String) {
        dao.likeById(token)
    }
}