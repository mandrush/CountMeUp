package com.wpam18l.countmeup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
    }

    fun saveExpense(view: View) {
        val enteredExpense = findViewById<EditText>(R.id.expenseEditText).text
        if (enteredExpense.isNullOrEmpty()) {
            Toast
                    .makeText(applicationContext, "Please enter any amount of money!", LENGTH_SHORT)
                    .show()
            return
        }
        Money.addExpense(enteredExpense.toString().toFloat())
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
    }
}
