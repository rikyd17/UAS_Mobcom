package com.dicoding.rotie.di

import com.dicoding.rotie.data.repository.AuthRepositoryImpl
import com.dicoding.rotie.data.repository.BreadRepositoryImpl
import com.dicoding.rotie.data.repository.OrderRepositoryImpl
import com.dicoding.rotie.domain.repository.AuthRepository
import com.dicoding.rotie.domain.repository.BreadRepository
import com.dicoding.rotie.domain.repository.OrderRepository
import com.dicoding.rotie.presentation.ui.screen.about.ProfileViewModel
import com.dicoding.rotie.presentation.ui.screen.cart.CartViewModel
import com.dicoding.rotie.presentation.ui.screen.detail.DetailViewModel
import com.dicoding.rotie.presentation.ui.screen.home.HomeViewModel
import com.dicoding.rotie.presentation.ui.screen.login.LoginViewModel
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val supabaseModule = module {
	single {
		createSupabaseClient(
			supabaseUrl = "https://leiowbrqulmqfzmchfwq.supabase.co",
			supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImxlaW93YnJxdWxtcWZ6bWNoZndxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDI4NjYyMDUsImV4cCI6MjAxODQ0MjIwNX0.2m4s8fYo99ZAh9_Mes4AU_--rA3SNRRXTEEHXO6KnaI"
		) {
			install(Auth)
			install(Postgrest)
			install(ComposeAuth) {
				googleNativeLogin("412532897150-1h5m3raf81pr9uibrblbppeh8m3lijd1.apps.googleusercontent.com")
			}
		}
	}
}

val repositoryModule = module {
	single<BreadRepository> { BreadRepositoryImpl(get()) }
	single<OrderRepository> { OrderRepositoryImpl(get()) }
	single<AuthRepository> { AuthRepositoryImpl(get()) }
}

val viewModelModule = module {
	viewModel { HomeViewModel(get()) }
	viewModel { DetailViewModel(get(), get()) }
	viewModel { CartViewModel(get()) }
	viewModel { LoginViewModel(get()) }
	viewModel { ProfileViewModel(get(), get()) }
}