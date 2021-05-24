package com.bangkit.slinguage.ui.splass

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bangkit.slinguage.data.login.Resource
import com.bangkit.slinguage.databinding.ActivitySplassNoLoginBinding
import com.bangkit.slinguage.ui.home.HomeActivity
import com.bangkit.slinguage.ui.login.LoginActivity
import com.bangkit.slinguage.ui.register.RegisterActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplassNoLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplassNoLoginBinding

    private val splassViewModel: SplassViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplassNoLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT


        splassViewModel.isLogin().observe(this, {
            when (it) {
                is Resource.Success -> {
                    if (it.data == true) {
                        Intent(this, HomeActivity::class.java).apply {
                            startActivity(this)
                            finish()
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Log.d("TAG", "onCreate: Cek login Loading")
                }
            }
        })
        binding.btnLogin.setOnClickListener {
            Intent(this@SplassNoLoginActivity, LoginActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.btnRegister.setOnClickListener {
            Intent(this@SplassNoLoginActivity, RegisterActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}