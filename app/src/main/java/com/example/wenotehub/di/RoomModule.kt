package com.example.wenotehub.di

import android.content.Context
import androidx.room.Room
import com.example.wenotehub.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "note_table"
    ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.noteDao()
}