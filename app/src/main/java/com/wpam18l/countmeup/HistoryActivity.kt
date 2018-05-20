package com.wpam18l.countmeup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class HistoryActivity : AppCompatActivity() {

    private val rowParser = classParser<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val expenseHistory: List<Expense> = database.use {
            select("Expense")
                    .orderBy("date", SqlOrderDirection.DESC)
                    .parseList(rowParser)
        }

        expenseHistory.iterator().forEach {
            println(it.toString())
        }

    }
}
