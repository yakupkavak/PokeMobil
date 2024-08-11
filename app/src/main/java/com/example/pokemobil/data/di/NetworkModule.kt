package com.example.pokemobil.data.di

import com.example.pokemobil.BuildConfig
import com.example.pokemobil.data.repository.PokemonRepository
import com.example.pokemobil.data.service.PokemonService
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
    fun provideRetrofitService(): PokemonService {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL_API)
            .build()
            .create(PokemonService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(pokeService: PokemonService): PokemonRepository {
        return PokemonRepository(pokeService)
    }
}