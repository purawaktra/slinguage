package com.bangkit.slinguage.data.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.slinguage.data.login.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class DataSource(private val auth: FirebaseAuth, private val firestore: FirebaseFirestore) {

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

    private fun getDetailUser(id: String): LiveData<Resource<User>> {
        val user = MutableLiveData<Resource<User>>()
        firestore
            .collection(USER_COLLECTION).document(id)
            .get()
            .addOnSuccessListener {
                val data = it.data as HashMap<String, Any>
                val result = User(
                    userId = id,
                    email = data[EMAIL_KEY] as String,
                    username = data[USERNAME_KEY] as String)
                user.postValue(Resource.Success(result))
            }.addOnFailureListener{
                user.postValue(Resource.Error(it.localizedMessage))
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


}