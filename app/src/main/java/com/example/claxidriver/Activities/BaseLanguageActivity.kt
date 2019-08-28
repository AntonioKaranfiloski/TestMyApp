package com.example.claxidriver.Activities

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.claxidriver.Utils.Utils

@SuppressLint("Registered")
open class BaseLanguageActivity :AppCompatActivity() {

var mMyApplication: MyApplication? = null

    override fun attachBaseContext(newBase: Context?) {
        val context = Utils.loadLanguage(MyApplication: mMyApplication ?: newBase)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
            super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            disableAutoFill()
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private fun disableAutoFill() {
        window.decorView.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
    }

}