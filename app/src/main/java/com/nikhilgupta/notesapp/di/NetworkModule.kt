package com.nikhilgupta.notesapp.di

import com.nikhilgupta.notesapp.api.AuthInterceptor
import com.nikhilgupta.notesapp.api.NotesAPI
import com.nikhilgupta.notesapp.api.UserAPI
import com.nikhilgupta.notesapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(authInterceptor: AuthInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder) : UserAPI {
        return retrofitBuilder.build().create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesNotesAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient) : NotesAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(NotesAPI::class.java)
    }
}