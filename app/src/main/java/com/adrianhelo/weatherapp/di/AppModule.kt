package com.adrianhelo.weatherapp.di

import android.annotation.SuppressLint
import android.content.Context
import com.adrianhelo.weatherapp.data.ApiServiceImp
import com.adrianhelo.weatherapp.data.LanguagePreference
import com.adrianhelo.weatherapp.data.UnitsPreference
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
    fun provideApiService(retrofit: Retrofit): ApiServiceImp{
        return retrofit.create(ApiServiceImp::class.java)
    }

    @SuppressLint("RestrictedApi")
    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): UnitsPreference {
        return UnitsPreference(context)
    }

    @Provides
    @Singleton
    fun provideLanguagePreference(@ApplicationContext context: Context): LanguagePreference{
        return LanguagePreference(context)
    }

}