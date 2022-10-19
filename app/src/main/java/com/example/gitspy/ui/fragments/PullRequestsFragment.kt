package com.example.gitspy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.gitspy.R
import com.example.gitspy.adapters.PrViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_pull_requests.*

class PullRequestsFragment : Fragment(R.layout.fragment_pull_requests) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val prVpAdapter = PrViewPagerAdapter(requireActivity().supportFragmentManager , lifecycle)
        prViewPager.adapter = prVpAdapter

        prTabLayout.setOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { prViewPager.setCurrentItem(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        prViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                prTabLayout.selectTab(prTabLayout.getTabAt(position))
            }
        })



    }
}