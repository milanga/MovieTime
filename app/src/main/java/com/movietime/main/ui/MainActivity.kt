package com.movietime.main.ui

import android.R.attr
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
//import net.openid.appauth.AuthorizationException
//import net.openid.appauth.AuthorizationResponse


@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainCompose()
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == 100 && resultCode == RESULT_OK){
//            val resp = AuthorizationResponse.fromIntent(data!!)
//            val ex = AuthorizationException.fromIntent(data)
//
//        }
//    }
}
