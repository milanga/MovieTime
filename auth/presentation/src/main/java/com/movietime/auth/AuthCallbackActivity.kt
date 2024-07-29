package com.movietime.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.movietime.domain.interactors.auth.StoreToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuthCallbackActivity: ComponentActivity() {

    @Inject
    lateinit var storeToken: StoreToken

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val code = intent.data?.getQueryParameter("code")
        //todo should handle if code is null
        GlobalScope.launch {
            storeToken(code!!)
        }
        finish()
    }
}