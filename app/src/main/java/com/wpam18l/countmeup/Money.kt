package com.wpam18l.countmeup

class Money {
    companion object {
        var expense: Float = 0f
        var monthlyIncome: Float = 0f
        var dailyLimit: Float = 0f
        fun addExpense(amount: Float) { expense += amount }
    }
}