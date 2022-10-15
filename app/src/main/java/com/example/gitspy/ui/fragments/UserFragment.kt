package com.example.gitspy.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gitspy.R
import com.example.gitspy.models.User
import com.example.gitspy.ui.activities.MainActivity
import com.example.gitspy.utility.CHANNEL_ID
import com.example.gitspy.utility.Resource
import com.example.gitspy.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment: Fragment(R.layout.fragment_user) {

    private lateinit var viewModel: MainViewModel
    private lateinit var  searchBox : EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        viewModel = (activity as MainActivity).viewModel


        userSearchButton.setOnClickListener(View.OnClickListener {
            val userName = searchBox.text.toString()
            if (userName!= ""){
                hideKeyBoard()
                viewModel.getUser(userName)
            }
        })

        userOpenInGithubBtn.setOnClickListener(View.OnClickListener {
            handleOpenInGithub()
        })

        viewModel.user.observe(viewLifecycleOwner , Observer {
            when(it){
                is Resource.Loading<User> -> {
                    userProgressBar.visibility = ProgressBar.VISIBLE
                }

                is Resource.Success<User> -> {
                    userErrorRL.visibility = View.GONE
                    userProfileCard.visibility = View.VISIBLE
                    userProgressBar.visibility = ProgressBar.GONE
                    it.data?.let { it1 -> handleUser(it1) }
                    Log.d("ABHI", "onViewCreated: ${it.data?.login}")
                }
                is Resource.Error<User> -> {
                    userProgressBar.visibility = ProgressBar.GONE
                    Glide.with(requireContext())
                        .load("https://octodex.github.com/images/homercat.png")
                        .into(errorImg)
                    userErrorMessage.text = "Oops!!! User not found"

                    userErrorRL.visibility = View.VISIBLE
                    userProfileCard.visibility = View.GONE

                    Log.d("ABHI", "onViewCreated: Error Happened during api call")
                }
            }
        })


    }

    private fun hideKeyBoard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken,0)
    }

    private fun initViews(view : View) {
        searchBox =  view.findViewById(R.id.userSearchBox)
        userProfileCard.visibility = View.GONE
        userErrorRL.visibility = View.VISIBLE
        Glide.with(requireContext())
            .load("https://www.openfl.org/images/home/OpenSource.png")
            .into(errorImg)
        userErrorMessage.text = "Welcome to GitSpy..."
    }

    private fun handleOpenInGithub() {
        viewModel.user.observe(viewLifecycleOwner , Observer {
            val bundle = Bundle()
            bundle.putString("url" , it.data?.html_url)
            findNavController().navigate(R.id.action_userFragment_to_githubFragment ,bundle)
        })

    }

    private fun handleUser(user : User) {
        Glide.with(requireContext())
            .load(user.avatar_url)
            .into(userProfileImg)

        userLoginNameTxt.text = user.name
        githubIdTxt.text = user.login
        userBioTxt.text = user.bio
        userRepos.text = user.public_repos.toString()
        userFollowers.text = user.followers.toString()
        userFollowing.text = user.following.toString()
        if(user.company != null){
            userJobTXT.text = user.company
        }
        else{
            userJobTXT.text = "Not available"
        }
        if (user.location!= null){
            userLocationTxt.text = user.location
        }
        else{
            userLocationTxt.text = "Not available"
        }
        if (user.blog!= ""){
            userWebsiteTxt.text = user.blog
        }
        else{
            userWebsiteTxt.text = "Not available"
        }
        if (user.twitter_username!= null){
            userTwitterTxt.text = user.twitter_username
        }
        else{
            userTwitterTxt.text = "Not available"
        }

    }
}