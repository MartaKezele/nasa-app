package mk.nasa.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import mk.nasa.framework.startAppNoDelay

class NasaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        startAppNoDelay(context)
    }
}