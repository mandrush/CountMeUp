package com.wpam18l.countmeup

class Ledger {

    companion object {

        var dailySpendings: Float = 0.0f
        var monthlySpendings: Float = 0.0f
        var expenseHistory: MutableList<Expense> = arrayListOf()
        val availableCurrencies = arrayListOf<String>("PLN", "USD", "EUR", "GBP")

        fun addToDailySpendings(x: Float) { dailySpendings += x }
        fun addToMontlySpendings(x: Float) { monthlySpendings += x }
        fun addToExpenseHistory(e: Expense) { expenseHistory.add(e) }

    }

}