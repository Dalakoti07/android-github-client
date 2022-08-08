package com.dalakoti07.android.githubclient

import android.app.Application
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GithubApp: Application() {
    override fun onCreate() {
        super.onCreate()

        initCoil()
    }

    private fun initCoil() {
        val imageLoader = ImageLoader.Builder(this).apply {
            crossfade(true)
            allowHardware(false)
            components {
                if (Build.VERSION.SDK_INT >= 28) add(ImageDecoderDecoder.Factory())
                else add(GifDecoder.Factory())
            }
        }
            .build()
        Coil.setImageLoader(imageLoader)
    }

}