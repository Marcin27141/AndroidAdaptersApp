package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.list3.databinding.FragmentList2Binding
import com.example.list3.databinding.FragmentSetInvitationBinding

class SetInvitationFragment : Fragment() {
    private lateinit var binding: FragmentSetInvitationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetInvitationBinding.inflate(inflater, container, false);

        val confirmButton: Button = binding.confirmButton
        val titleEdit: EditText = binding.titleEdit
        val authorInfoEdit: EditText = binding.authorInfoEdit

        confirmButton.setOnClickListener { _ ->
            //val value = edit.text.toString()
            requireActivity().supportFragmentManager.setFragmentResult("invitationInfo",
                bundleOf("title" to titleEdit.text.toString(), "authorInfo" to authorInfoEdit.text.toString())
            )
            findNavController().navigate(R.id.mainScreen)
        }

        return binding.root
    }
}