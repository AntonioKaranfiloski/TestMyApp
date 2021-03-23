package com.example.claxidriver.Activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class DispatchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)


        val prefs = AppPrefs(this)
        if (prefs.token.isEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
        }else if (prefs.firebaseRegistrationId != null && prefs.firebaseRegistrationId!!.isNotEmpty()) {
            MyFirebaseMessagingService.sendRegistrationToServer(prefs.firebaseRegistrationId, this)
        }
        startActivity(Intent(this, MainActivity::class.java))
        SyncDataService.startActionGetMyPlaces(this)
        SyncDataService.startActionGetPromoCodes(this)
        SyncDataService.startActionGetNotifications(this)
//firebase
//proveri ovde ne sum siguren dali e tocno finish()
    finish()
}}
