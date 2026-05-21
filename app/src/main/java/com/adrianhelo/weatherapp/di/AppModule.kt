package com.adrianhelo.weatherapp.di

import android.annotation.SuppressLint
import android.content.Context
import com.adrianhelo.weatherapp.data.ApiService
import com.adrianhelo.weatherapp.data.UnitsPreferenceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @SuppressLint("RestrictedApi")
    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): UnitsPreferenceImp {
        return UnitsPreferenceImp(context)
    }

}