package com.bangkit.slinguage.ui.home.member

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.slinguage.R
import com.bangkit.slinguage.databinding.ActivityMemberBinding

class MemberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}