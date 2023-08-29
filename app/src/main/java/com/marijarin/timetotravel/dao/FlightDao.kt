package com.marijarin.timetotravel.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marijarin.timetotravel.entity.FlightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Query("SELECT * FROM FlightEntity")
    fun getAll(): Flow<List<FlightEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishes: List<FlightEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dish: FlightEntity)

    @Query("""
        UPDATE FlightEntity SET
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE searchToken = :token
        """)
    suspend fun likeById(token: String)
}