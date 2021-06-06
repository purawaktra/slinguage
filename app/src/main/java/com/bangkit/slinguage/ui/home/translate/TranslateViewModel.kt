package com.bangkit.slinguage.ui.home.translate

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.slinguage.data.source.Repository

class TranslateViewModel(private val repository: Repository): ViewModel() {

    fun upFirebase(fileName: String, bitmap: Bitmap) = repository.upImage(fileName, bitmap)

    fun getPredict(url: String) = repository.getPredict(url)

}