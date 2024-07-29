package com.example.pokemobil.di

import com.example.pokemobil.repository.PokemonRepository
import com.example.pokemobil.service.RetrofitAPI
import com.example.pokemobil.util.const.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitService(): RetrofitAPI{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: RetrofitAPI): PokemonRepository{
        return PokemonRepository(api)
    }
}