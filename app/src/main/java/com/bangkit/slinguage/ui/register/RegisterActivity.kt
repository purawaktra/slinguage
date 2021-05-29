package com.bangkit.slinguage.ui.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bangkit.slinguage.data.source.Resource
import com.bangkit.slinguage.databinding.ActivityRegisterBinding
import com.bangkit.slinguage.ui.login.LoginActivity
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT
        val username = binding.email
        val name = binding.username
        val password = binding.password
        val confirPass = binding.confirmPassword
        val register = binding.register
        val loading = binding.progressBar

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerFormState = it ?: return@Observer

            // disable login button unless both username / password is valid
            register.isEnabled = registerFormState.isDataValid
            if (registerFormState.nameError != null) {
                name.error = getString(registerFormState.nameError)
            }
            if (registerFormState.usernameError != null) {
                username.error = getString(registerFormState.usernameError)
            }
            if (registerFormState.passwordError != null) {
                password.error = getString(registerFormState.passwordError)
            }
            if (registerFormState.confirPasswordError != null) {
                confirPass.error = getString(registerFormState.confirPasswordError)
            }
        })



        username.afterTextChanged {
            registerViewModel.registerDataChanged(
                name.text.toString(),
                username.text.toString(),
                password.text.toString(),
                confirPass.text.toString()
            )
        }

        name.afterTextChanged {
            registerViewModel.registerDataChanged(
                name.text.toString(),
                username.text.toString(),
                password.text.toString(),
                confirPass.text.toString()
            )
        }
        password.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    name.text.toString(),
                    username.text.toString(),
                    password.text.toString(),
                    confirPass.text.toString()
                )
            }
        }

        confirPass.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    name.text.toString(),
                    username.text.toString(),
                    password.text.toString(),
                    confirPass.text.toString()

                )
            }



            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registerViewModel.register(
                            username.text.toString(),
                            password.text.toString(),
                            confirPass.text.toString()
                        )
                }
                false
            }

            register.setOnClickListener {
                loading.visibility = View.VISIBLE
                registerViewModel.register(
                    name = name.text.toString(),
                    email = username.text.toString(),
                    password = password.text.toString()
                ).observe(this@RegisterActivity, {
                    when (it) {
                        is Resource.Success -> {
                            loading.visibility = View.GONE
                            it.data?.let { it1 -> updateUiWithUser(it1) }
                        }
                        is Resource.Error -> {

                            loading.visibility = View.GONE
                            showLoginFailed(it.message)
                        }
                        is Resource.Loading -> loading.visibility = View.VISIBLE
                    }

                })
            }
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateUiWithUser(model: String) {

        Toast.makeText(
            applicationContext,
            model,
            Toast.LENGTH_LONG
        ).show()

        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun showLoginFailed(errorString: String?) {
        Toast.makeText(applicationContext, "ERROR $errorString", Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}