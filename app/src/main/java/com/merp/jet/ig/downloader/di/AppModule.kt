package com.merp.jet.ig.downloader.di

import android.content.Context
import androidx.room.Room
import com.merp.jet.ig.downloader.data.InstaReelDao
import com.merp.jet.ig.downloader.data.InstaReelDatabase
import com.merp.jet.ig.downloader.data.preference.InstaReelDataStore
import com.merp.jet.ig.downloader.network.ReelApi
import com.merp.jet.ig.downloader.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideReelApi(): ReelApi {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReelApi::class.java)
    }

    @Singleton
    @Provides
    fun provideInstaReelDao(instaReelDatabase: InstaReelDatabase): InstaReelDao {
        return instaReelDatabase.getInstaReelDao()
    }

    @Singleton
    @Provides
    fun provideInstaReelDatabase(@ApplicationContext context: Context): InstaReelDatabase {
        return Room.databaseBuilder(context, InstaReelDatabase::class.java, Constants.INSTA_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideInstaReelDataStore(@ApplicationContext context: Context) =
        InstaReelDataStore(context)
}