package com.onemain.challenge.koinmodules

import com.onemain.challenge.data.ResponseHandler
import com.onemain.challenge.repository.DadRepository
import com.onemain.challenge.repository.DadRepositoryImpl
import com.onemain.challenge.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module
 */
val appModule= module {
   single { ResponseHandler(androidContext()) }
    single<DadRepository> { DadRepositoryImpl(get(),get()) }
    viewModel { MainViewModel(get())}
}

