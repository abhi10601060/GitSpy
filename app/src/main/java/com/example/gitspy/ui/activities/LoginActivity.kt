package com.example.gitspy.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.gitspy.R
import com.example.gitspy.models.AccessToken
import com.example.gitspy.network.GitSpyService
import com.example.gitspy.network.RetroInstance
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    override fun onResume() {
        super.onResume()

        var uri : Uri? = intent.data
        Log.d("ABHI", "onResume: received uri is ${uri.toString()}")

        uri?.let {
            if (it.toString().startsWith(CALLBACK_URI)){
                var code  = it.getQueryParameter("code").toString()
                Log.d("ABHI", "onResume: received code is $code")
                getToken(code)
            }
        }

    }

    private fun getToken(code: String) {
        val api = RetroInstance.getGithubInstance().create(GitSpyService::class.java)
        val call = api.getToken(CLIENT_ID , CLIENT_SECRET , code)
        call.enqueue(object  : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                response.body()?.let {
                    login_txt_gitspy.setText(it.access_token.toString())
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

}