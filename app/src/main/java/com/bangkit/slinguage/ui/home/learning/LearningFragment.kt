package com.bangkit.slinguage.ui.home.learning

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.slinguage.R
import com.bangkit.slinguage.databinding.FragmentHomeBinding
import com.bangkit.slinguage.ui.home.education.EducationActivity
import com.bangkit.slinguage.ui.home.member.MemberActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LearningFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.member.setOnClickListener {
            Intent(requireActivity(), MemberActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.learning.setOnClickListener {
            Intent(requireActivity(), EducationActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}