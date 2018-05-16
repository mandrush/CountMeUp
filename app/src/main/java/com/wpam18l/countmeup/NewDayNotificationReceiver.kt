package com.wpam18l.countmeup

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import java.util.*

class NewDayNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val now = GregorianCalendar.getInstance()
        if (now.get(Calendar.HOUR_OF_DAY) == 0) {
            Ledger.dailySpendings = 0.0f
            val prefs = context?.getSharedPreferences("Preferences", 0)
            prefs
                    ?.edit()
                    ?.putFloat("DailySpendings", 0.0f)
                    ?.apply()
        }
        if (now.get(Calendar.DAY_OF_MONTH) == 1) {
            Ledger.monthlySpendings = 0.0f
            val prefs = context?.getSharedPreferences("Preferences", 0)
            prefs
                    ?.edit()
                    ?.putFloat("MonthlySpendings", 0.0f)
                    ?.apply()
        }
    }
}