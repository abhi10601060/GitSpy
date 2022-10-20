package com.example.gitspy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitspy.R
import com.example.gitspy.adapters.ReleaseAdapter
import com.example.gitspy.models.releases.Releases
import com.example.gitspy.ui.activities.StatsActivity
import com.example.gitspy.utility.Resource
import com.example.gitspy.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_releases.*

class ReleasesFragment  : Fragment(R.layout.fragment_releases) {

    lateinit var viewModel : StatsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as StatsActivity).viewModel

        viewModel.releases.observe(viewLifecycleOwner , Observer {
            when(it){
                is Resource.Success<Releases> ->{
                    val adapter = ReleaseAdapter()
                    adapter.submitList(it.data)
                    releasesRV.adapter = adapter
                    releasesRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
                }
            }
        })
    }
}