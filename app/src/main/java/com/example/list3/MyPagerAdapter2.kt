package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter2(fa: FragmentActivity): FragmentStateAdapter(fa) {
    val PAGE_COUNT: Int = 2
    val TAB_ICONS: IntArray = intArrayOf(R.drawable.bus_icon, R.drawable.airplane_icon)
    override fun getItemCount(): Int {
        return PAGE_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SetInvitationFragment()
            else -> SliderFragment()
        }
    }

}