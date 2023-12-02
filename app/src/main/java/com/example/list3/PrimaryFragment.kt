package com.example.list3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.list3.databinding.FragmentPrimaryBinding
import com.example.list3.databinding.FragmentSetInvitationBinding


class PrimaryFragment : Fragment() {
    private lateinit var binding: FragmentPrimaryBinding
    private lateinit var titleLabel: TextView
    private lateinit var authorLabel: TextView
    private lateinit var preferencesManager: PreferencesManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrimaryBinding.inflate(inflater, container, false);

        titleLabel = binding.textHome
        authorLabel = binding.authorInfo

        preferencesManager = PreferencesManager.getInstance()
        setInitialInfos(titleLabel.text.toString(), authorLabel.text.toString(), requireActivity())
        val initialValues = preferencesManager.getTitleAndAuthor(requireActivity())
        titleLabel.text = initialValues.first
        authorLabel.text = initialValues.second

//        requireActivity().supportFragmentManager.setFragmentResultListener("invitationInfo", viewLifecycleOwner) {
//                _, bundle ->
//            val invitation = bundle.getString("title")
//            val authorInfo = bundle.getString("authorInfo")
//            titleLabel.text = invitation
//            authorLabel.text = authorInfo
//        }

        return binding.root
    }

    companion object Companion {
        private var wasInitialized: Boolean = false;
        @JvmStatic
        fun setInitialInfos(title: String, author: String, activity: FragmentActivity) {
            if (!wasInitialized) {
                PreferencesManager.getInstance().setHomeInfo(title, author, activity)
                wasInitialized = true
            }
        }
    }
}