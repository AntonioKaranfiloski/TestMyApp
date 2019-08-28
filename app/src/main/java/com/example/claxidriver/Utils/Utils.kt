package com.example.claxidriver.Utils

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.claxidriver.Activities.DispatchActivity
import java.util.*

object Utils {

    fun showToastMessageLong(context: Context?, string: String){
if (context != null){
    val toast =Toast.makeText(context, string, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.show()
}
}
    fun showToastMessageShort(context: Context?, string: String){
        if (context != null){
            val toast =Toast.makeText(context, string, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.show()
        }
    }
    fun restartApp(context: Context?){
        if (context != null){
            val intent = Intent(context, DispatchActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }
    fun logOutUnautorizedUser(context: Context?){
        if (context != null){
            AppPrefs.resetUser(context)
            restartApp(context)
        }
    }
    fun isNetworkAvaible(context: Context?): Boolean{
        return if (context != null){
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            networkInfo != null &&networkInfo.isConnected
        }else{
            false
        }
    }
    fun isLocationServicesEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        return if (locationManager == null) {
            false
        } else {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }
    }
   fun loadLanguage(context: Context?):ContextWrapper{
       val config = Configuration(context.resources.configuration)
       val prefs = AppPrefs(context)

       val sysLocale: Locale?
       sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           getSystemLocale(config)
       } else {
           getSystemLocaleLegacy(config)
       }
       if (sysLocale.language != prefs.language) {
           val locale = Locale(prefs.language)
           Locale.setDefault(locale)
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               setSystemLocale(config, locale)
               //context.createConfigurationContext(config)
           } else {
               setSystemLocaleLegacy(config, locale)
               //context.resources.updateConfiguration(config, context.resources.displayMetrics)
           }
       }

       //todo - this works, but is deprecated
       if (context != null) {
           context.resources.updateConfiguration(config, context.resources.displayMetrics)
       }

       return ContextWrapper(context)
   }
    @Suppress("DEPRECATION")
    private fun getSystemLocaleLegacy(config: Configuration): Locale {
        return config.locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun getSystemLocale(config: Configuration): Locale {
        return config.locales.get(0)
    }

    @Suppress("DEPRECATION")
    private fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
        config.locale = locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun setSystemLocale(config: Configuration, locale: Locale) {
        config.setLocale(locale)
    }
   }
