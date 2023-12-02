package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.list3.databinding.FragmentPrimaryBinding
import com.example.list3.databinding.FragmentSetInvitationBinding


class PrimaryFragment : Fragment() {
    private lateinit var binding: FragmentPrimaryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrimaryBinding.inflate(inflater, container, false);

        val titleLabel: TextView = binding.textHome
        val authorLabel: TextView = binding.authorInfo

        requireActivity().supportFragmentManager.setFragmentResultListener("invitationInfo", viewLifecycleOwner) {
                _, bundle ->
            val invitation = bundle.getString("title")
            val authorInfo = bundle.getString("authorInfo")
            titleLabel.text = invitation
            authorLabel.text = authorInfo
        }

        return binding.root
    }
}