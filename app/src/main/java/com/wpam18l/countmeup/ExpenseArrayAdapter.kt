package com.wpam18l.countmeup

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat

class ExpenseArrayAdapter(ctx: Context, res: Int, expenseList: List<Expense>) : ArrayAdapter<Expense>(ctx, res, expenseList) {

    var resource: Int
    var expenseList: List<Expense>
    var inflater: LayoutInflater

    init {
        this.resource = res
        this.expenseList = expenseList
        this.inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var listItem: View? = convertView

        if (listItem == null) {
           listItem = inflater.inflate(resource, null)
        }

        val currentExpense: Expense = expenseList.get(position)

        val image = listItem?.findViewById<ImageView>(R.id.historyImageView)
        when (currentExpense.category) {
            "Food" -> image?.setImageResource(R.drawable.food_category)
            "Bills" -> image?.setImageResource(R.drawable.bills_category)
            "Home" -> image?.setImageResource(R.drawable.home_category)
            "Medicine" -> image?.setImageResource(R.drawable.medicine_category)
            "Travel" -> image?.setImageResource(R.drawable.travel_category)
            else -> image?.setImageResource(R.drawable.icon_ok)
        }

        val expenseRow = listItem?.findViewById<TextView>(R.id.rowExpenseText)
        expenseRow?.text = "${currentExpense.expense} ${currentExpense.currency}"
        val dateRow = listItem?.findViewById<TextView>(R.id.expenseDateText)
        val d = currentExpense.date
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        sdf.timeZone = java.util.TimeZone.getTimeZone("GMT+2")
        val date = sdf.format(d)
        dateRow?.text = "$date"

        return listItem!!
    }
}