package com.example.gitspy.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gitspy.ui.fragments.issue_fragments.ClosedIssueFragment
import com.example.gitspy.ui.fragments.issue_fragments.IssueNotificationFragment
import com.example.gitspy.ui.fragments.issue_fragments.OpenIssueFragment

class IssueViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager , lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if (position==1){
            return OpenIssueFragment()
        }
        if (position==2){
            return ClosedIssueFragment()
        }
        return IssueNotificationFragment()
    }

}