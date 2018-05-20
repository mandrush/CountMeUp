package com.wpam18l.countmeup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import org.jetbrains.anko.db.insert
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity(){

//    private var currencyList = arrayListOf<String>("PLN", "USD", "EUR", "GBP")
//    private var categoryList = arrayListOf<String>("Food", "Bills", "Home", "Medicine", "Travel")

    private var selectedCurrency: String? = null
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val currencySpinner = findViewById(R.id.currencySpinner) as Spinner
        val categorySpinner = findViewById(R.id.categorySpinner) as Spinner

        val currencySpinnerAA = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                Ledger.availableCurrencies)

        val categorySpinnerAA = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                Ledger.availableCategories)

        currencySpinner.adapter = currencySpinnerAA
        categorySpinner.adapter = categorySpinnerAA

        currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCurrency = currencySpinner.selectedItem.toString()
            }
        }
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = categorySpinner.selectedItem.toString()
            }
        }
    }

    fun saveExpense(view: View) {
        val enteredAmountText = findViewById<EditText>(R.id.expenseEditText).text
        if (enteredAmountText.isNullOrEmpty()) {
            Toast
                    .makeText(applicationContext, "Please enter any amount of money!", LENGTH_SHORT)
                    .show()
            return
        }
        val enteredAmount: Float = enteredAmountText.toString().toFloat()
        val expenseDate = Date().time
        val expense = Expense(enteredAmount, selectedCurrency, selectedCategory, expenseDate)
        Ledger.addToDailySpendings(enteredAmount)
        Ledger.addToMontlySpendings(enteredAmount)
        Ledger.addToExpenseHistory(expense)

//        save to preferences
        val prefs = applicationContext.getSharedPreferences("Preferences", 0)
        prefs.edit().putFloat("DailySpendings", Ledger.dailySpendings).apply()
        prefs.edit().putFloat("MonthlySpendings", Ledger.monthlySpendings).apply()

//        save to DB
        saveExpenseToDB(enteredAmount, selectedCurrency, selectedCategory, expenseDate)

//        back to main activity
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
    }

    private fun saveExpenseToDB(amount: Float?, currency: String?, category: String?, date: Long?) {
        database.use {
            insert("Expense",
                    "amount" to amount,
                    "currency" to currency,
                    "category" to category,
                    "date" to date)
        }
    }
}
