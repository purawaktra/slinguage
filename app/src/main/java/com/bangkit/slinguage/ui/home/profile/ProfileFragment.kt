package com.bangkit.slinguage.ui.home.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.slinguage.R
import com.bangkit.slinguage.data.login.Resource
import com.bangkit.slinguage.databinding.FragmentProfileBinding
import com.bangkit.slinguage.ui.login.LoginActivity
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logout.setOnClickListener {
            profileViewModel.logout().observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Error -> {
                        Toast.makeText(context, "Logout Error", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> Log.d("TAG", "logout loading: ")
                    is Resource.Success -> {
                        Toast.makeText(context, "Logout Succes", Toast.LENGTH_SHORT).show()
                        Intent(requireActivity(), LoginActivity::class.java).apply {
                            startActivity(this)
                            requireActivity().finish()
                        }
                    }
                }
            })
        }
    }
}