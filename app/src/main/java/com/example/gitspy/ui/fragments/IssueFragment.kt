package com.example.gitspy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.gitspy.R
import com.example.gitspy.adapters.IssueViewPagerAdapter
import com.example.gitspy.ui.activities.StatsActivity
import com.example.gitspy.viewmodels.StatsViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_issues.*

class IssueFragment : Fragment(R.layout.fragment_issues) {

    lateinit var viewModel : StatsViewModel
    lateinit var  issueViewPager : ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as StatsActivity).viewModel

        issueViewPager = view.findViewById(R.id.issueViewPager)
        val VPAdapter = IssueViewPagerAdapter(requireActivity().supportFragmentManager , lifecycle)
        issueViewPager.adapter = VPAdapter

       issueTabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
           override fun onTabSelected(tab: TabLayout.Tab?) {
               tab?.position?.let { issueViewPager.setCurrentItem(it) }
           }

           override fun onTabUnselected(tab: TabLayout.Tab?) {

           }

           override fun onTabReselected(tab: TabLayout.Tab?) {

           }

       })

        issueViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                issueTabLayout.selectTab(issueTabLayout.getTabAt(position))
            }
        })
    }
}