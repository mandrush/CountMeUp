package com.wpam18l.countmeup

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class FirstTimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time)

        val defaultCurrencySpinner = findViewById(R.id.defaultCurrencySpinner) as Spinner
        val defaultCurrencySpinnerAA = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                Ledger.availableCurrencies )
        defaultCurrencySpinner.adapter = defaultCurrencySpinnerAA
        defaultCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                UserSettings.defaultCurrency = defaultCurrencySpinner.selectedItem.toString()
            }
        }


    }

    fun setDefaultSettings(view: View) {
        val monthlyIncome = findViewById<EditText>(R.id.monthlyEditText).text
        val dailyLimit = findViewById<EditText>(R.id.dailyLimitEditText).text

        if (monthlyIncome.isNullOrEmpty()) {
            Toast
                    .makeText(applicationContext, "Please enter your monthly income", Toast.LENGTH_SHORT)
                    .show()
            return
        }
        if (dailyLimit.isNullOrEmpty()) {
            Toast
                    .makeText(applicationContext, "Please enter your daily expense limit", Toast.LENGTH_SHORT)
                    .show()
            return
        }
        UserSettings.monthlyIncome = monthlyIncome.toString().toFloat()
        UserSettings.dailyLimit = dailyLimit.toString().toFloat()

        saveInPrefs()
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
    }

    private fun saveInPrefs() {
        val prefs: SharedPreferences = applicationContext.getSharedPreferences("Preferences", 0)
        val editor = prefs.edit()
        editor.putBoolean("FirstTimeSet", true)
        editor.putFloat("MonthlyIncome", UserSettings.monthlyIncome)
        editor.putFloat("DailyLimit", UserSettings.dailyLimit)
        editor.putString("Currency", UserSettings.defaultCurrency)
        editor.apply()
    }
}
