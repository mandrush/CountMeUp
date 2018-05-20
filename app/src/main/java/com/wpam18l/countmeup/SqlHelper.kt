package com.wpam18l.countmeup

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class SqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDB") {

    companion object {
        private var instance: SqlHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): SqlHelper {
            if (instance == null) {
                instance = SqlHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("Expense", true,
                "amount" to REAL,
                "currency" to TEXT,
                "category" to TEXT,
                "date" to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}

val Context.database: SqlHelper
    get() = SqlHelper.getInstance(applicationContext)