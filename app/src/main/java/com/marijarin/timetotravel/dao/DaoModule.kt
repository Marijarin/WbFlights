package com.marijarin.timetotravel.dao

import com.marijarin.timetotravel.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    @Provides
    fun provideDishDao(db: AppDb): FlightDao = db.flightDao()
}
