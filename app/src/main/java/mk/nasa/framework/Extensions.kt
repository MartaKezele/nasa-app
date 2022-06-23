package mk.nasa.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.preference.PreferenceManager
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import java.time.LocalDate

fun View.startAnimation(animationId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, animationId))

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })

fun Context.setBooleanPreference(key: String, value: Boolean) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

fun Context.setDatePreference(key: String, value: LocalDate) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putLong(key, value.toEpochDay())
        .apply()

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)

fun Context.getDatePreference(key: String): LocalDate? {
    val epochDate = PreferenceManager.getDefaultSharedPreferences(this)
        .getLong(key, Long.MIN_VALUE)

    return if (epochDate == Long.MIN_VALUE) {
        null
    } else {
        LocalDate.ofEpochDay(epochDate)
    }
}

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
    return false
}

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))