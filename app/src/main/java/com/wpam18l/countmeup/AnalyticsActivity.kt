package com.wpam18l.countmeup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLogTags
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

class AnalyticsActivity : AppCompatActivity() {

    private val rowParser = rowParser {amount: Float -> amount  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        var pieChart = findViewById<PieChart>(R.id.PieChart)
        pieChart.holeRadius = 20.0f
        pieChart.centerText = "Expense by category"
        pieChart.setCenterTextSize(10.0f)
        pieChart.setDrawEntryLabels(true)

        val foodExpenseList: List<Float> = database.use {
            select("Expense", "amount")
                    .whereArgs("category = {cat}", "cat" to "Food")
                    .parseList(rowParser)
        }

        val billsExpenseList: List<Float> = database.use {
            select("Expense", "amount")
                    .whereArgs("category = {cat}", "cat" to "Bills")
                    .parseList(rowParser)
        }

        val homeExpenseList: List<Float> = database.use {
            select("Expense", "amount")
                    .whereArgs("category = {cat}", "cat" to "Home")
                    .parseList(rowParser)
        }

        val medicineExpenseList: List<Float> = database.use {
            select("Expense", "amount")
                    .whereArgs("category = {cat}", "cat" to "Medicine")
                    .parseList(rowParser)
        }

        val travelExpenseList: List<Float> = database.use {
            select("Expense", "amount")
                    .whereArgs("category = {cat}", "cat" to "Travel")
                    .parseList(rowParser)
        }

        var sum = 0.0f
        val expensesByCategory = arrayListOf(foodExpenseList, billsExpenseList, homeExpenseList, medicineExpenseList, travelExpenseList)
        var summed: MutableList<Float> = arrayListOf()
        expensesByCategory.forEach { list ->
            list.forEach { element ->
                sum += element
            }
            summed.add(sum)
            sum = 0.0f
        }

    }
}
