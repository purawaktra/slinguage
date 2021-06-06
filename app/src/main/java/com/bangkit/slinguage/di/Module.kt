package com.bangkit.slinguage.di

import com.bangkit.slinguage.data.source.DataSource
import com.bangkit.slinguage.data.source.Repository
import com.bangkit.slinguage.data.source.remote.ApiService
import com.bangkit.slinguage.ui.home.education.EducationViewModel
import com.bangkit.slinguage.ui.home.profile.ProfileViewModel
import com.bangkit.slinguage.ui.home.translate.TranslateViewModel
import com.bangkit.slinguage.ui.login.LoginViewModel
import com.bangkit.slinguage.ui.register.RegisterViewModel
import com.bangkit.slinguage.ui.splass.SplassViewModel
import com.bangkit.slinguage.utils.JsonHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val firebaseModule = module {
    factory { FirebaseAuth.getInstance() }
    factory { FirebaseFirestore.getInstance() }
//    factory { Firebase.database.reference }
}


val repositoryModule = module {
    single { DataSource(get(), get(), get(), get()) }
    single { Repository(get()) }
    single { JsonHelper(get()) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.122.140.171/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SplassViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EducationViewModel(get()) }
    viewModel { TranslateViewModel(get()) }


}