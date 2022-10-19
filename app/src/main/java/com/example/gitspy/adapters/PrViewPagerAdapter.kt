package com.example.gitspy.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gitspy.ui.fragments.prs_fragment.MergedPrFragment
import com.example.gitspy.ui.fragments.prs_fragment.OpenPrFragment
import com.example.gitspy.ui.fragments.prs_fragment.PrNotificationsFragment

class PrViewPagerAdapter(fragmentManager: FragmentManager , lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 1){
            return OpenPrFragment()
        }
        if(position == 2){
            return MergedPrFragment()
        }

        return PrNotificationsFragment()
    }


}