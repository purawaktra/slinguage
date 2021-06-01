package com.bangkit.slinguage.ui.home.education

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.slinguage.data.source.Repository
import com.bangkit.slinguage.data.source.Resource
import com.bangkit.slinguage.data.source.model.Education

class EducationViewModel(private val repository: Repository): ViewModel() {

    fun getListAlphabet() : LiveData<Resource<List<Education>>> = repository.getEducation()
}