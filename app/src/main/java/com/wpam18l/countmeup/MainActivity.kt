package com.wpam18l.countmeup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //TODO PROGRESS BARY
//    TODO dodanie daty wydatku jako property klasy Expense?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = applicationContext.getSharedPreferences("Preferences", 0)
        if (prefs.getBoolean("FirstTimeSet", true)) {
            val i = Intent(applicationContext, FirstTimeActivity::class.java)
            startActivity(i)
        }
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

    }

    fun addExpenseOnClick(view: View) {
        val i = Intent(applicationContext, AddExpenseActivity::class.java)
        startActivity(i)
    }


}
