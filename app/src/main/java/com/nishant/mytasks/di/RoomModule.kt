package com.nishant.mytasks.di

import android.content.Context
import androidx.room.Room
import com.nishant.mytasks.room.CacheMapper
import com.nishant.mytasks.room.TaskDao
import com.nishant.mytasks.room.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideTaskDb(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTaskDAO(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()
    }

    @Singleton
    @Provides
    fun provideCacheMapper(): CacheMapper {
        return CacheMapper()
    }
}