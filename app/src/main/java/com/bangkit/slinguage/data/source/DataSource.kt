package com.bangkit.slinguage.data.source

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.slinguage.data.source.model.Education
import com.bangkit.slinguage.data.source.model.User
import com.bangkit.slinguage.data.source.remote.ApiService
import com.bangkit.slinguage.data.source.remote.ImagePost
import com.bangkit.slinguage.data.source.remote.Predict
import com.bangkit.slinguage.utils.JsonHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream


class DataSource(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val jsonHelper: JsonHelper,
    private val apiService: ApiService
) {

    companion object {
        private const val USER_COLLECTION = "Users"
        private const val USERNAME_KEY = "username"
        private const val EMAIL_KEY = "email"

    }

    fun register(email: String, password: String, username: String): LiveData<Resource<String>> {
        val registerResult = MutableLiveData<Resource<String>>()
        registerResult.postValue(Resource.Loading(null))
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser = auth.currentUser
                    Log.d("TAG", "register: auth success")
                    Log.d("TAG", "register: ${currentUser?.uid}")

                    val result = "Register Success"
                    if (currentUser != null) {
                        val ref = firestore.collection(USER_COLLECTION).document(currentUser.uid)
                        val user: MutableMap<String, Any> = HashMap()
                        user["username"] = username
                        user["email"] = email
                        ref.set(user).addOnSuccessListener {
                            Log.d("TAG", "register: Create User Success!")

                        }.addOnFailureListener {
                            Log.d("TAG", "register: onFailure! ")
                        }
                        registerResult.postValue(Resource.Success(result))
                    }
                }
            }
            .addOnFailureListener {
                Log.d("TAG", "register: onfailure")
                registerResult.postValue(Resource.Error(it.localizedMessage))
            }
        return registerResult
    }

    fun getDetailUser(): LiveData<Resource<User>> {
        val id = auth.currentUser?.uid
        val user = MutableLiveData<Resource<User>>()
        if (id != null) {
            firestore
                .collection(USER_COLLECTION).document(id)
                .get()
                .addOnSuccessListener {
                    val data = it.data as HashMap<String, Any>
                    val result = User(
                        userId = id,
                        email = data[EMAIL_KEY] as String,
                        username = data[USERNAME_KEY] as String
                    )
                    user.postValue(Resource.Success(result))
                }.addOnFailureListener {
                    user.postValue(Resource.Error(it.localizedMessage))
                }
        }


        return user
    }

    fun login(email: String, password: String): LiveData<Resource<User>> {
        val user = MutableLiveData<Resource<User>>()
        user.postValue(Resource.Loading(null))
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val currentUser = auth.currentUser

                val result = User(userId = currentUser?.uid, email = currentUser?.email)
                user.postValue(Resource.Success(result))
            }
            .addOnFailureListener {
                user.postValue(Resource.Error(it.message.toString()))

            }
        return user
    }

    fun logout(): LiveData<Resource<Boolean>> {
        val logout = MutableLiveData<Resource<Boolean>>()
        try {
            auth.signOut()
            logout.postValue(Resource.Success(true))
        } catch (e: Exception) {
            logout.postValue(Resource.Error(e.message.toString()))
        }
        return logout
    }

    fun isLogin(): LiveData<Resource<Boolean>> {
        val login = MutableLiveData<Resource<Boolean>>()
        try {
            if (auth.currentUser != null) {
                login.postValue(Resource.Success(true))
            } else {
                login.postValue(Resource.Success(false))
            }
        } catch (e: Exception) {
            login.postValue(Resource.Error<Boolean>(e.message.toString()))
        }

        return login
    }

    fun getEducation(): LiveData<Resource<List<Education>>> {
        val education = MutableLiveData<Resource<List<Education>>>()
//        education.postValue(Resource.Loading(null))

        education.value = Resource.Success(jsonHelper.loadEducation())

//        val mDatabase = FirebaseDatabase.getInstance().reference
//        val mReference = mDatabase.child("education-resource")
//
//        mReference.get().addOnCompleteListener {
//            if (it.isSuccessful) {
//                val result = it.result
//                Log.d("TAG", "getEducation: SUCCES atas")
//                education.postValue(Resource.Success(result.children.map {snapshot ->
//                    snapshot.getValue(Education::class.java)!!
//                }))
//
//            } else {
//                Log.d("TAG", "getEducation: ERROR atas")
//
//                education.postValue(Resource.Error(it.exception.toString()))
//            }
//        }


        return education

    }


    fun upImageDetect(fileName: String, uri: Uri): LiveData<Resource<String>> {



        val urlDownload = MutableLiveData<Resource<String>>()
        urlDownload.postValue(Resource.Loading(null))

        val storage = Firebase.storage("gs://slinguage-bangkit-acedemy")

        val storageRef = storage.reference
        val imagesRef = storageRef.child("detect/$fileName")

        val uploadTask  = imagesRef.putFile(uri)


        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                Log.d("TAG", "continueTask: ERROR")
                task.exception?.let {
                    throw it
                }
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                urlDownload.postValue(Resource.Success(downloadUri.toString()))
                Log.d("TAG", "dowloadUri: $downloadUri")
            } else {
                urlDownload.postValue(Resource.Error(task.exception.toString()))
                Log.d("TAG", "get URL : ERROR")
            }
        }


        return urlDownload

    }


    fun getPredict(url: String): LiveData<Resource<Predict>>{

        val result = MutableLiveData<Resource<Predict>>()
        result.postValue(Resource.Loading(null))
        val body = ImagePost(url)

        apiService.predict(body).enqueue(object : Callback<Predict> {
            override fun onResponse(call: Call<Predict>, response: Response<Predict>) {

                if (response.isSuccessful){
                    val hasil = response.body()
                    if (hasil != null){
                        result.postValue(Resource.Success(hasil))
                    }
                }

            }

            override fun onFailure(call: Call<Predict>, t: Throwable) {
                result.postValue(Resource.Error(t.toString()))
            }

        })
        return result
    }


    fun uploadImage(fileName: String, bitmap: Bitmap): LiveData<Resource<String>> {
        val urlDownload = MutableLiveData<Resource<String>>()
        urlDownload.postValue(Resource.Loading(null))

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()
        val storage = Firebase.storage("gs://slinguage-bangkit-acedemy")
        val storageRef = storage.reference
        val imagesRef = storageRef.child("detect/$fileName")

        val uploadTask = imagesRef.putBytes(data)


        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                Log.d("TAG", "continueTask: ERROR")
                task.exception?.let {
                    throw it
                }
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                urlDownload.postValue(Resource.Success(downloadUri.toString()))
                Log.d("TAG", "dowloadUri: $downloadUri")
            } else {
                urlDownload.postValue(Resource.Error(task.exception.toString()))
                Log.d("TAG", "get URL : ERROR")
            }
        }


        return urlDownload
    }
}