package com.bangkit.slinguage.ui.home.learning

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
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
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed(this@LearningFragment)
        }
        callback.isEnabled
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

    private fun onBackPressed(fragmentActivity: LearningFragment) {
        AlertDialog.Builder(fragmentActivity.requireContext())
            .setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
            .setMessage("Are you sure?")
            .setPositiveButton("yes") { _, _ ->
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                fragmentActivity.requireActivity().finish()
            }.setNegativeButton("no", null).show()
    }
}
