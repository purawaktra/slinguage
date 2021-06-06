package com.bangkit.slinguage.data.source

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import com.bangkit.slinguage.data.source.model.Education
import com.bangkit.slinguage.data.source.model.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class Repository(private val dataSource: DataSource) {

    fun logout() : LiveData<Resource<Boolean>> =
        dataSource.logout()

    fun login(email: String, password: String): LiveData<Resource<User>> =
        dataSource.login(email, password)

    fun isLogin(): LiveData<Resource<Boolean>> =
        dataSource.isLogin()


    fun register(name: String, email: String, password: String): LiveData<Resource<String>> =
        dataSource.register(email = email, password = password, username = name)

    fun getUser():LiveData<Resource<User>> =
        dataSource.getDetailUser()

    fun getEducation(): LiveData<Resource<List<Education>>> =
        dataSource.getEducation()


    fun upFirebase(fileNam: String, uri: Uri) : LiveData<Resource<String>> =
        dataSource.upImageDetect(fileNam, uri)

    fun upImage(fileNam: String, bitmap: Bitmap) : LiveData<Resource<String>> =
        dataSource.uploadImage(fileNam, bitmap)

    fun getPredict(url: String) = dataSource.getPredict(url)
}