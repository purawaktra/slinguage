package com.bangkit.slinguage.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.slinguage.data.source.Repository
import com.bangkit.slinguage.data.source.Resource
import com.bangkit.slinguage.data.source.model.User

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    fun getUser(): LiveData<Resource<User>> = repository.getUser()
    fun logout(): LiveData<Resource<Boolean>> = repository.logout()
}