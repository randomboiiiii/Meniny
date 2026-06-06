package sk.kubik.meniny

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sk = Locale("sk", "SK")
        val cal = Calendar.getInstance(sk)
        val key = SimpleDateFormat("MM-dd", sk).format(cal.time)
        val date = SimpleDateFormat("EEEE, d. MMMM yyyy", sk).format(cal.time)
            .replaceFirstChar { it.titlecase(sk) }

        findViewById<TextView>(R.id.info_date).text = date
        findViewById<TextView>(R.id.info_meniny).text = "Meniny má: ${Meniny.forKey(key)}"

        // Update widgety pri otvorení appky
        MeninyWidget.updateAll(this)
        MeninyWidget.scheduleMidnight(this)
    }
}
