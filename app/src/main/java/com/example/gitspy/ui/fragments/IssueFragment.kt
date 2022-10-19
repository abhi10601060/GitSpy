package com.example.gitspy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.gitspy.R
import com.example.gitspy.adapters.IssueAdapter
import com.example.gitspy.adapters.IssueNotificationAdapter
import com.example.gitspy.adapters.IssueViewPagerAdapter
import com.example.gitspy.models.issues.Issues
import com.example.gitspy.ui.activities.StatsActivity
import com.example.gitspy.utility.Resource
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



//    private fun handleTabs(fullName : String) {
//        issueTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                when(tab?.id){
//                   0->{
//                        viewModel.issue.observe(viewLifecycleOwner, Observer {
//                            if(it!=null){
//                                notificationAdapter = IssueNotificationAdapter()
//                                notificationAdapter.submitList(it.reversed())
//                                issueRV.adapter = notificationAdapter
//                                issueRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
//                            }
//                        })
//                    }
//                    R.id.open_issue_tab ->{
//                        viewModel.getOpenIssues(fullName)
//                        viewModel.OpenIssues.observe(viewLifecycleOwner , Observer {
//                            when(it){
//                                is Resource.Success<Issues> -> {
//                                    val adapter = IssueAdapter()
//                                    adapter.submitList(it.data)
//                                    issueRV.adapter = adapter
//                                    issueRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
//                                }
//                            }
//                        })
//
//                    }
//                    2->{
//
//                    }
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//
//        })
//    }
}