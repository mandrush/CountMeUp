package com.wpam18l.countmeup

class Expense (private var _expense: Float, _currency: String?, _category: String?) {

    var expense: Float = _expense
        set(value) {
            if (value < 0) {
                throw IllegalArgumentException("You cannot spend negative money.")
            }
        }
    val category: String? = _category
    val currency: Currency? = when(_currency) {
        "PLN" -> Currency.PLN
        "USD" -> Currency.USD
        "EUR" -> Currency.EUR
        "GBP" -> Currency.GBP
        else -> throw IllegalArgumentException("Undefined currency, critical error.")
    }

}