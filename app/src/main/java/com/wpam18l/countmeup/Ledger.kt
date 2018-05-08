package com.wpam18l.countmeup

class Ledger {

    companion object {

        var dailySpendings: Float = 0.0f
        var monthlySpendings: Float = 0.0f
        var expenseHistory: MutableList<Expense> = arrayListOf()

        fun addToDailySpendings(x: Float) { dailySpendings += x }
        fun addToMontlySpendings(x: Float) { monthlySpendings += x }
        fun addToExpenseHistory(e: Expense) { expenseHistory.add(e) }

    }

}