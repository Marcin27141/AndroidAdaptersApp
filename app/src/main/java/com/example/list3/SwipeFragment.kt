package com.example.list3

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.list3.databinding.FragmentSwipeBinding
import com.google.android.material.tabs.TabLayoutMediator

class SwipeFragment : Fragment() {
    private lateinit var binding: FragmentSwipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSwipeBinding.inflate(inflater, container, false);

        val viewPagerAdapter = MyPagerAdapter2(requireActivity())
        val viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, viewPager
        ) { tab, position ->
            tab.text = "Tab ${(position + 1)}"
            tab.setIcon(viewPagerAdapter.TAB_ICONS[position])
        }.attach()

        return binding.root
        }
}