package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.list3.databinding.FragmentSetImageBinding
import com.example.list3.databinding.FragmentSwipeBinding
import com.google.android.material.tabs.TabLayoutMediator

class SetImageFragment : Fragment() {
    private lateinit var binding: FragmentSetImageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetImageBinding.inflate(inflater, container, false);

        val viewPagerAdapter = ImageAdapter(requireActivity())
        val viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter

        val initialIcon = PreferencesManager.getInstance().getHomeIcon(requireActivity())
        viewPager.setCurrentItem(viewPagerAdapter.getIconTabNumber(initialIcon), false)

        val setButton = binding.setButton
        setButton.setOnClickListener {
            val currentTab = viewPager.currentItem
            val iconSrc = viewPagerAdapter.getCurrentIconSrc(currentTab)
            PreferencesManager.getInstance().setHomeIcon(iconSrc, requireActivity())
            findNavController().navigate(R.id.mainScreen)
        }

        return binding.root
    }

    class ImageAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        val PAGE_COUNT: Int = 4
        val PAGE_ICONS: IntArray = intArrayOf(
            R.drawable.bird_icon,
            R.drawable.predator_icon,
            R.drawable.rodent_icon,
            R.drawable.insect_icon)
        override fun getItemCount(): Int {
            return PAGE_COUNT
        }

        fun getCurrentIconSrc(currentTab: Int) : Int {
            return PAGE_ICONS[currentTab]
        }

        fun getIconTabNumber(iconSrc: Int): Int {
            return PAGE_ICONS.indexOf(iconSrc)
        }

        override fun createFragment(position: Int): Fragment {
            return ShowIconFragment.newInstance(PAGE_ICONS[position])
        }

    }
}