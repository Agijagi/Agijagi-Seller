package com.likelion.agijagiseller.feature.login.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.likelion.agijagiseller.global.userDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserLocalModule {
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.userDataStore
    }
}
