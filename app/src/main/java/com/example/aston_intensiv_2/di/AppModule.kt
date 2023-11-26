package com.example.aston_intensiv_2.di

import com.example.aston_intensiv_2.data.PieDataRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRepo(): PieDataRepo {
        return PieDataRepo
    }
}