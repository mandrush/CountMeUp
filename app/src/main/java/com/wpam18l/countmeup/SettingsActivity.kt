package com.wpam18l.countmeup

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val settingsCurrencySpinner = findViewById(R.id.currencySettingsSpinner) as Spinner
        val settingsCurrencySpinnerAA = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                Ledger.availableCurrencies )
        settingsCurrencySpinner.adapter = settingsCurrencySpinnerAA
        settingsCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                UserSettings.defaultCurrency = settingsCurrencySpinner.selectedItem.toString()
            }
        }

    }

    fun saveSettings(view: View) {
        val monthlyIncome = findViewById<EditText>(R.id.monthlyIncomeSettingsEdit).text
        val dailyLimit = findViewById<EditText>(R.id.dailyLimitSettingsEditText).text

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
        Toast
                .makeText(applicationContext,
                        "New monthly income is $monthlyIncome, " +
                        "daily limit is $dailyLimit, " +
                        "default currency is ${UserSettings.defaultCurrency}",
                        Toast.LENGTH_LONG)
                .show()
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
    }

    private fun saveInPrefs() {
        val prefs: SharedPreferences = applicationContext.getSharedPreferences("Preferences", 0)
        val editor = prefs.edit()
        editor.putFloat("MonthlyIncome", UserSettings.monthlyIncome)
        editor.putFloat("DailyLimit", UserSettings.dailyLimit)
        editor.putString("Currency", UserSettings.defaultCurrency)
        editor.apply()
    }

}
