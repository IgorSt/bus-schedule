package com.example.bbusschedule

import android.app.Application
import com.example.bbusschedule.database.AppDatabase

class BusScheduleApplication: Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}