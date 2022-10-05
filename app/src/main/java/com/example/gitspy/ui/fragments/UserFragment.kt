package com.example.gitspy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.gitspy.R
import com.example.gitspy.models.User
import com.example.gitspy.ui.activities.MainActivity
import com.example.gitspy.utility.Resource
import com.example.gitspy.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment: Fragment(R.layout.fragment_user) {

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        viewModel.getUser("abhi10601060")
        viewModel.user.observe(viewLifecycleOwner , Observer {
            when(it){
                is Resource.Loading<User> -> {
                    userProgressBar.visibility = ProgressBar.VISIBLE
                }

                is Resource.Success<User> -> {
                    userProgressBar.visibility = ProgressBar.GONE
                    txtUser.text = it.data?.name
                    Log.d("ABHI", "onViewCreated: ${it.data?.login}")
                }
                is Resource.Error<User> -> {
                    userProgressBar.visibility = ProgressBar.GONE
                    txtUser.text = it.message
                    Log.d("ABHI", "onViewCreated: Error Happened during api call")
                }
            }
        })


    }
}