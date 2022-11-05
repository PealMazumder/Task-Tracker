package com.example.tasktracker.di.modules

import android.content.Context
import com.example.tasktracker.network.ApiService
import com.example.tasktracker.utils.Constants
import com.example.tasktracker.utils.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule() {

    @Singleton
    @Provides
    fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        val token = SharedPreferenceManager(context).getValue(Constants.USER_TOKEN)
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }

        return builder.build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(@ApplicationContext context: Context): ApiService {
        return providesRetrofit(provideClient(context)).create(ApiService::class.java)
    }
}