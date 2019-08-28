package com.example.claxidriver.Activities

import android.annotation.SuppressLint

@SuppressLint("Registered")
open class BaseEventActivity : BaseLanguageActivity() {
    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }
}