package com.bangkit.slinguage.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.slinguage.R
import com.bangkit.slinguage.data.source.Repository
import com.bangkit.slinguage.data.source.Resource

class RegisterViewModel(private val repository: Repository): ViewModel() {


    fun register(name: String, email: String, password: String): LiveData<Resource<String>> =
        repository.register(name, email, password)

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm


    fun registerDataChanged(name: String, username: String, password: String, confirPassword: String) {
        if (!isNameValid(name)){
            _registerForm.value = RegisterFormState(nameError = R.string.empty_username)
        }else if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if(!isConfirPasswordValid(password, confirPassword )){
            _registerForm.value = RegisterFormState(confirPasswordError = R.string.different_password)
        }else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isConfirPasswordValid(password: String, confirPass: String): Boolean {
        return password == confirPass
    }
}