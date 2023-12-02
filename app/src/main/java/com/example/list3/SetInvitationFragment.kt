package com.example.list3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.list3.databinding.FragmentList2Binding
import com.example.list3.databinding.FragmentSetInvitationBinding

class SetInvitationFragment : Fragment() {
    private lateinit var binding: FragmentSetInvitationBinding
    private lateinit var preferencesManager: PreferencesManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetInvitationBinding.inflate(inflater, container, false);



        val confirmButton: Button = binding.confirmButton
        val titleEdit: EditText = binding.titleEdit
        val authorInfoEdit: EditText = binding.authorInfoEdit

        preferencesManager = PreferencesManager.getInstance()
        val initialValues = preferencesManager.getTitleAndAuthor(requireActivity())
        titleEdit.setText(initialValues.first)
        authorInfoEdit.setText(initialValues.second)



        confirmButton.setOnClickListener { _ ->
            //val value = edit.text.toString()
//            requireActivity().supportFragmentManager.setFragmentResult("invitationInfo",
//                bundleOf("title" to titleEdit.text.toString(), "authorInfo" to authorInfoEdit.text.toString())
//            )
            preferencesManager.setHomeInfo(titleEdit.text.toString(), authorInfoEdit.text.toString(),requireActivity())
            findNavController().navigate(R.id.mainScreen)
        }

        return binding.root
    }


}