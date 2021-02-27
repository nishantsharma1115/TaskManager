package com.nishant.mytasks.di

import com.nishant.mytasks.repositories.DataRepository
import com.nishant.mytasks.room.CacheMapper
import com.nishant.mytasks.room.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataRepository(
        taskDao: TaskDao,
        cacheMapper: CacheMapper
    ): DataRepository {
        return DataRepository(taskDao, cacheMapper)
    }
}