package com.example.fampayproject

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate



class ContextualCardsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        lateinit var instance: ContextualCardsApplication
        fun getContext(): Context = instance.applicationContext
    }
}
