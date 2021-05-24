package com.bangkit.slinguage.ui.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.bangkit.slinguage.R
import com.bangkit.slinguage.data.login.Resource
import com.bangkit.slinguage.data.login.model.User
import com.bangkit.slinguage.databinding.ActivityLoginBinding
import com.bangkit.slinguage.ui.home.HomeActivity
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.progressBar



        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })



        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString()).observe(this@LoginActivity,{
                    when (it){
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

                    setResult(Activity.RESULT_OK)

                    //Complete and destroy login activity once successful
                    finish()
                })
            }
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

    }

    private fun updateUiWithUser(model: User) {
        val welcome = getString(R.string.welcome)
        val displayName = model.email
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()

        Intent(this, HomeActivity::class.java).apply {
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