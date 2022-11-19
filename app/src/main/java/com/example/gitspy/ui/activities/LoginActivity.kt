package com.example.gitspy.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.gitspy.R


const val CLIENT_ID = "a6eebbf6dd9422605a92"
const val CLIENT_SECRET = "b0b40372ad636f373efd2ee544700fca7dc06a6e"
const val CALLBACK_URI = "gitspy://callback"

class LoginActivity : AppCompatActivity() {

    lateinit var authenticate : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authenticate = findViewById(R.id.login_btn_authenticate)

        authenticate.setOnClickListener(View.OnClickListener {
            var intent  = Intent(Intent.ACTION_VIEW , Uri.parse("http://github.com/login/oauth/authorize?client_id=$CLIENT_ID&scope=repo&redirect_uri=$CALLBACK_URI"))
            startActivity(intent)
        })

    }

}