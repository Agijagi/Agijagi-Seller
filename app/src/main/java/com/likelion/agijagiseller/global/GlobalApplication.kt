package com.likelion.agijagiseller.global

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.likelion.agijagiseller.BuildConfig

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}
