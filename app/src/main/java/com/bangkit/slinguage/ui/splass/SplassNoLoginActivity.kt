package com.bangkit.slinguage.ui.splass

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.slinguage.databinding.ActivitySplassNoLoginBinding
import com.bangkit.slinguage.ui.login.LoginActivity
import com.bangkit.slinguage.ui.register.RegisterActivity

class SplassNoLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplassNoLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplassNoLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT

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