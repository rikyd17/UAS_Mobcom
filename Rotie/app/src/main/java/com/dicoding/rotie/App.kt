package com.dicoding.rotie

import android.app.Application
import com.dicoding.rotie.di.repositoryModule
import com.dicoding.rotie.di.supabaseModule
import com.dicoding.rotie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidLogger()
			androidContext(this@App)
			modules(listOf(repositoryModule, viewModelModule, supabaseModule))
		}
	}
}