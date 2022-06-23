package mk.nasa.framework

import android.content.Context
import android.os.Handler
import android.os.Looper
import mk.nasa.R
import mk.nasa.activities.HostActivity
import mk.nasa.activities.IntroActivity

fun callDelayed(delay: Long, function: Runnable) =
    Handler(Looper.getMainLooper()).postDelayed(function, delay)

fun call(function: Runnable) =
    Handler(Looper.getMainLooper()).post(function)

fun startAppWithDelay(context: Context, delay: Long) {
    if (context.getBooleanPreference(context.getString(R.string.viewed_intro_slides))) {
        callDelayed(delay) { context.startActivity<HostActivity>() }
    } else {
        callDelayed(delay) { context.startActivity<IntroActivity>() }
    }
}

fun startAppNoDelay(context: Context) {
    if (context.getBooleanPreference(context.getString(R.string.viewed_intro_slides))) {
        call() { context.startActivity<HostActivity>() }
    } else {
        call() { context.startActivity<IntroActivity>() }
    }
}