package com.bangkit.slinguage.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.slinguage.data.login.Repository
import com.bangkit.slinguage.data.login.Resource

class ProfileViewModel(private val repository: Repository) : ViewModel() {


    fun logout(): LiveData<Resource<Boolean>> = repository.logout()
}