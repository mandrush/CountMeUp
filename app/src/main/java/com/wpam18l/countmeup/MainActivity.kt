package com.wpam18l.countmeup

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    //TODO PROGRESS BARY
//    TODO dodanie daty wydatku jako property klasy Expense?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = applicationContext.getSharedPreferences("Preferences", 0)
//        prefs.edit().putBoolean("FirstTimeSet", false).apply()
//        load the settings
        if (!prefs.getBoolean("FirstTimeSet", true)) {
            val i = Intent(applicationContext, FirstTimeActivity::class.java)
            startActivity(i)
        }
        UserSettings.monthlyIncome = prefs.getFloat("MonthlyIncome", UserSettings.monthlyIncome)
        UserSettings.dailyLimit = prefs.getFloat("DailyLimit", UserSettings.dailyLimit)

        setContentView(R.layout.activity_main)
        val dailyMoneyText = findViewById<TextView>(R.id.dailyMoneyText)
        val monthlyMoneyText = findViewById<TextView>(R.id.monthlyMoneyText)

        monthlyMoneyText.text = getString(
                R.string.monthly_expenditure,
                Ledger.monthlySpendings,
                UserSettings.monthlyIncome)

        dailyMoneyText.text = getString(
                R.string.daily_expenditure,
                Ledger.dailySpendings,
                UserSettings.dailyLimit)

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

    }

    fun addExpenseOnClick(view: View) {
        val i = Intent(applicationContext, AddExpenseActivity::class.java)
        startActivity(i)
    }


}
