package com.wpam18l.countmeup

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

//    TODO analityka?
//    TODO jak starczy czasu - kosmetyka?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkDate()
        val prefs = applicationContext.getSharedPreferences("Preferences", 0)
//        prefs.edit().putBoolean("FirstTimeSet", false).apply()
//        load the settings
        if (!prefs.getBoolean("FirstTimeSet", true)) {
            val i = Intent(applicationContext, FirstTimeActivity::class.java)
            startActivity(i)
        }
        UserSettings.monthlyIncome = prefs.getFloat("MonthlyIncome", UserSettings.monthlyIncome)
        UserSettings.dailyLimit = prefs.getFloat("DailyLimit", UserSettings.dailyLimit)
        Ledger.dailySpendings = prefs.getFloat("DailySpendings", Ledger.dailySpendings)
        Ledger.monthlySpendings = prefs.getFloat("MonthlySpendings", Ledger.monthlySpendings)


        setContentView(R.layout.activity_main)
        val dailyMoneyText = findViewById<TextView>(R.id.dailyMoneyText)
        val monthlyMoneyText = findViewById<TextView>(R.id.monthlyMoneyText)

        monthlyMoneyText.text = getString(
                R.string.monthly_expenditure,
                Ledger.monthlySpendings,
                UserSettings.monthlyIncome,
                UserSettings.defaultCurrency)

        dailyMoneyText.text = getString(
                R.string.daily_expenditure,
                Ledger.dailySpendings,
                UserSettings.dailyLimit,
                UserSettings.defaultCurrency)

        val dailyProgressBar = findViewById<ProgressBar>(R.id.dailyProgressBar)
        val monthlyProgressBar = findViewById<ProgressBar>(R.id.monthlyProgressBar)
        var dailyProgress = ((Ledger.dailySpendings / UserSettings.dailyLimit)*100).roundToInt()
        var monthlyProgress = ((Ledger.monthlySpendings / UserSettings.monthlyIncome)*100).roundToInt()

        if (dailyProgress > 100) {
            dailyProgress = 100
            dailyProgressBar.progressTintList = ColorStateList.valueOf(Color.RED)
        }
        if (monthlyProgress > 100) {
            monthlyProgress = 100
            monthlyProgressBar.progressTintList = ColorStateList.valueOf(Color.RED)
        }

        dailyProgressBar.progress = dailyProgress
        monthlyProgressBar.progress = monthlyProgress

        println(java.util.Calendar.getInstance())
        val download = Download()
        try {
            download.execute()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_history) {
            val i = Intent(applicationContext, HistoryActivity::class.java)
            startActivity(i)
        }
        else if (item?.itemId == R.id.menu_settings) {
            val i = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    fun addExpenseOnClick(view: View) {
        val i = Intent(applicationContext, AddExpenseActivity::class.java)
        startActivity(i)
    }

    private fun checkDate() {
        val dayIntent = Intent(applicationContext, NewDayNotificationReceiver::class.java)
        val pending = PendingIntent.getBroadcast(applicationContext, 0, dayIntent ,PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                50000,
                pending)
    }

    inner class Download : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {

            var result = ""
            var url: URL
            var httpURLConnection: HttpURLConnection
            val APIKey = "173de9569d3322f038ccf0635b4583a5"

            try {
                url = URL("http://data.fixer.io/api/latest?access_key=$APIKey")
                httpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                var data = inputStreamReader.read()

                while (data > 0) {
                    val character = data.toChar()
                    result += character

                    data = inputStreamReader.read()
                }
                return result

            } catch (e: Exception) {
                e.printStackTrace()
                return result
            }

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {
                val JSONObject = JSONObject(result)
                println(JSONObject)
                val rates = JSONObject.getString("rates")
                val ratesJson = JSONObject(rates)
                Ledger.PLNinEUR = ratesJson.getString("PLN").toFloat()
                Ledger.GBPinEUR = ratesJson.getString("GBP").toFloat()
                Ledger.USDinEUR = ratesJson.getString("USD").toFloat()
                println("${Ledger.GBPinEUR}, ${Ledger.USDinEUR}, ${Ledger.PLNinEUR}")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


}
