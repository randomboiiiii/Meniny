package sk.kubik.meniny

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        MeninyWidget.updateAll(context)
        MeninyWidget.scheduleMidnight(context)
    }
}
