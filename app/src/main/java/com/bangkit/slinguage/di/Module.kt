package com.bangkit.slinguage.di

import com.bangkit.slinguage.data.source.DataSource
import com.bangkit.slinguage.data.source.Repository
import com.bangkit.slinguage.ui.home.profile.ProfileViewModel
import com.bangkit.slinguage.ui.login.LoginViewModel
import com.bangkit.slinguage.ui.register.RegisterViewModel
import com.bangkit.slinguage.ui.splass.SplassViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel


val firebaseModule = module {
    factory { FirebaseAuth.getInstance() }
    factory { FirebaseFirestore.getInstance() }
}


val repositoryModule = module {
    single { DataSource(get(), get()) }
    single { Repository(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SplassViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileViewModel(get()) }

}