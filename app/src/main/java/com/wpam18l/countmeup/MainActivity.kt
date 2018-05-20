package com.wpam18l.countmeup

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.wpam18l.countmeup.R.id.text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

//    TODO konwersja walut
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


}
