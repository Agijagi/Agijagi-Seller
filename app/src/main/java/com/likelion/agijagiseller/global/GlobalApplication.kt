package com.likelion.agijagiseller.global

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kakao.sdk.common.KakaoSdk
import com.likelion.agijagiseller.BuildConfig
import dagger.hilt.android.HiltAndroidApp

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "userPreferences")

@HiltAndroidApp
class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}
