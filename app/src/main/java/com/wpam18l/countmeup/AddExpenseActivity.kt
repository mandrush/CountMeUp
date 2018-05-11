package com.wpam18l.countmeup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT

class AddExpenseActivity : AppCompatActivity(){

    private var currencyList = arrayListOf<String>("PLN", "USD", "EUR", "GBP")
    private var categoryList = arrayListOf<String>("Food", "Bills", "Home", "Medicine", "Travel")

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
                currencyList)

        val categorySpinnerAA = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                categoryList)

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
        val expense = Expense(enteredAmount, selectedCurrency, selectedCategory)
        Ledger.addToDailySpendings(enteredAmount)
        Ledger.addToMontlySpendings(enteredAmount)
        Ledger.addToExpenseHistory(expense)
//        Expense.addExpense(enteredAmountText.toString().toFloat())
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
    }
}
