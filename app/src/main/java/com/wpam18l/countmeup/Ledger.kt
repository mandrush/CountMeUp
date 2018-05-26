package com.wpam18l.countmeup

import android.os.AsyncTask
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class Ledger {

    companion object {

        var dailySpendings: Float = 0.0f
        var monthlySpendings: Float = 0.0f
        var expenseHistory: MutableList<Expense> = arrayListOf()
        var PLNinEUR:Float = 0.0f
        var GBPinEUR:Float = 0.0f
        var USDinEUR:Float = 0.0f

        val availableCurrencies = arrayListOf("PLN", "USD", "EUR", "GBP")
        val availableCategories = arrayListOf("Food", "Bills", "Home", "Medicine", "Travel")

        fun addToDailySpendings(x: Float) { dailySpendings += x }
        fun addToMontlySpendings(x: Float) { monthlySpendings += x }
        fun addToExpenseHistory(e: Expense) { expenseHistory.add(e) }

        fun convertAmount(
                startingCurrency: String?,
                amount: Float): Float {

            val defaultCurrency =  UserSettings.defaultCurrency
            var convertedAmount: Float

            convertedAmount =
                    when(startingCurrency) {
                        "PLN" -> when(defaultCurrency) {
                            "PLN" -> amount
                            "USD" -> amount * (USDinEUR / PLNinEUR)
                            "EUR" -> amount * PLNinEUR
                            "GBP" -> amount * (GBPinEUR / PLNinEUR)
                            else -> throw Exception("Conversion fatal error; currency not found $defaultCurrency")
                        }
                        "USD" -> when(defaultCurrency) {
                            "PLN" -> amount * (PLNinEUR / USDinEUR)
                            "USD" -> amount
                            "EUR" -> amount * USDinEUR
                            "GBP" -> amount * (GBPinEUR / USDinEUR)
                            else -> throw Exception("Conversion fatal error; currency not found $defaultCurrency")
                        }
                        "GBP" -> when(defaultCurrency) {
                            "PLN" -> amount * (PLNinEUR / GBPinEUR)
                            "USD" -> amount * (USDinEUR / GBPinEUR)
                            "EUR" -> amount * GBPinEUR
                            "GBP" -> amount
                            else -> throw Exception("Conversion fatal error; currency not found $defaultCurrency")
                        }
                        "EUR" -> when(defaultCurrency) {
                            "PLN" -> amount * PLNinEUR
                            "USD" -> amount * USDinEUR
                            "EUR" -> amount
                            "GBP" -> amount * GBPinEUR
                            else -> throw Exception("Conversion fatal error; currency not found $defaultCurrency")
                        }
                        else -> throw Exception("Conversion fatal error; currency not found $startingCurrency")
                    }

            return convertedAmount
        }


    }



}