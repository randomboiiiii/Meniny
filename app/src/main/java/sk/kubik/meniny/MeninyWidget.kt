package sk.kubik.meniny

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MeninyWidget : AppWidgetProvider() {

    companion object {
        const val ACTION_UPDATE = "sk.kubik.meniny.ACTION_UPDATE"
        private val SK = Locale("sk", "SK")

        fun updateAll(context: Context) {
            val mgr = AppWidgetManager.getInstance(context)
            val ids = mgr.getAppWidgetIds(
                android.content.ComponentName(context, MeninyWidget::class.java)
            )
            for (id in ids) updateWidget(context, mgr, id)
        }

        fun updateWidget(context: Context, mgr: AppWidgetManager, id: Int) {
            val cal = Calendar.getInstance(SK)
            val key = SimpleDateFormat("MM-dd", SK).format(cal.time)

            // Deň v týždni s veľkým prvým písmenom
            val dayName = SimpleDateFormat("EEEE", SK).format(cal.time)
                .replaceFirstChar { it.titlecase(SK) }
            // Dátum napr. "6. júna 2026"
            val dateStr = SimpleDateFormat("d. MMMM yyyy", SK).format(cal.time)

            val meniny = Meniny.forKey(key)

            val views = RemoteViews(context.packageName, R.layout.widget_meniny)
            views.setTextViewText(R.id.tv_day, dayName)
            views.setTextViewText(R.id.tv_date, dateStr)
            views.setTextViewText(R.id.tv_meniny_label, "Meniny má")
            views.setTextViewText(R.id.tv_meniny, meniny)

            // Klik = manuálny refresh
            val intent = Intent(context, MeninyWidget::class.java).apply {
                action = ACTION_UPDATE
            }
            val pi = PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, pi)

            mgr.updateAppWidget(id, views)
        }

        // Naplánuje update na najbližšiu polnoc
        fun scheduleMidnight(context: Context) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, MeninyWidget::class.java).apply {
                action = ACTION_UPDATE
            }
            val pi = PendingIntent.getBroadcast(
                context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val next = Calendar.getInstance(SK).apply {
                add(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 5)
                set(Calendar.MILLISECOND, 0)
            }
            val triggerInMs = next.timeInMillis - System.currentTimeMillis()
            am.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + triggerInMs,
                AlarmManager.INTERVAL_DAY,
                pi
            )
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (id in appWidgetIds) updateWidget(context, appWidgetManager, id)
        scheduleMidnight(context)
    }

    override fun onEnabled(context: Context) {
        scheduleMidnight(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_UPDATE ||
            intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_TIME_CHANGED ||
            intent.action == Intent.ACTION_TIMEZONE_CHANGED ||
            intent.action == "android.intent.action.DATE_CHANGED"
        ) {
            updateAll(context)
            scheduleMidnight(context)
        }
    }
}
