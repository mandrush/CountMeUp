package com.wpam18l.countmeup

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_expense_dialog.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addExpenseButton.setOnClickListener {
            val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.add_expense_dialog, null)

            val expensePopup = PopupWindow(popupView,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT)

            expensePopup.elevation = 10.0F
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                expensePopup.enterTransition = slideIn

                val slideOut = Slide()
                slideOut.slideEdge = Gravity.BOTTOM
                expensePopup.exitTransition = slideOut
            }

            val saveExpenseButton: Button = popupView.findViewById(R.id.button_save_expense)
            saveExpenseButton.setOnClickListener {
                Toast.makeText(applicationContext,"Expense saved!", Toast.LENGTH_SHORT).show()
            }

            val closeExpensePopupButton: Button = popupView.findViewById(R.id.button_close_expense)
            closeExpensePopupButton.setOnClickListener {
                expensePopup.dismiss()
            }

            TransitionManager.beginDelayedTransition(root_layout)
            expensePopup.showAtLocation(
                    root_layout,
                    Gravity.CENTER,
                    0,
                    0)
        }

    }


}
