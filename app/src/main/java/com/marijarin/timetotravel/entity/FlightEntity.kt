package com.marijarin.timetotravel.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marijarin.timetotravel.dto.Flight

@Entity
data class FlightEntity (
    @PrimaryKey
    val searchToken: String,
    val startDate: String,
    val endDate: String,
    val startLocationCode: String,
    val endLocationCode: String,
    val startCity: String,
    val endCity: String,
    val price: Int,
    var likedByMe: Boolean
){
    fun toDto() = Flight(
        searchToken = searchToken,
        startDate = startDate,
        endDate = endDate,
        startLocationCode = startLocationCode,
        endLocationCode = endLocationCode,
        startCity = startCity,
        endCity = endCity,
        price = price,
        likedByMe = likedByMe
    )
    companion object {
        fun fromDto(dto: Flight) = FlightEntity(
            dto.searchToken,
            dto.startDate,
            dto.endDate,
            dto.startLocationCode,
            dto.endLocationCode,
            dto.startCity,
            dto.endCity,
            dto.price,
            dto.likedByMe
        )
    }
}
fun List<Flight>.toEntity(): List<FlightEntity> = map(FlightEntity::fromDto)
