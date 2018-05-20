package com.wpam18l.countmeup

class Expense (
        private var _expense: Float,
        private var _currency: String?,
        private var _category: String?,
        private var _date: Long?) {

    var expense: Float = _expense
        set(value) {
            if (value < 0) {
                throw IllegalArgumentException("You cannot spend negative money.")
            }
        }
    var category: String? = _category
    var currency: Currency? = when(_currency) {
        "PLN" -> Currency.PLN
        "USD" -> Currency.USD
        "EUR" -> Currency.EUR
        "GBP" -> Currency.GBP
        else -> throw IllegalArgumentException("Undefined currency, critical error.")
    }
    var date: Long? = _date

    override fun toString(): String {
        return "This expense is $expense $currency of $category category done at $date"
    }
}