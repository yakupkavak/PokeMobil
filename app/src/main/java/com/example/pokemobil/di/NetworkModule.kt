package com.example.pokemobil.di

import com.example.pokemobil.repository.PokemonRepository
import com.example.pokemobil.service.PokemonService
import com.example.pokemobil.util.ServiceConst.BASE_URL
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
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(pokeService: PokemonService): PokemonRepository {
        return PokemonRepository(pokeService)
    }
}