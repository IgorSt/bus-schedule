package com.example.bbusschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bbusschedule.database.schedule.Schedule
import com.example.bbusschedule.database.schedule.ScheduleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class BusScheduleViewModel(
    private val scheduleDao: ScheduleDao
): ViewModel() {

    fun fullSchedule(): Flow<List<Schedule>> = scheduleDao.getAll()

    fun scheduleForStopName(name: String): Flow<List<Schedule>> = scheduleDao.getByStopName(name)

    class BusScheduleViewModelFactory(
        private val scheduleDao: ScheduleDao
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(BusScheduleViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BusScheduleViewModel(scheduleDao) as T
            }
            throw IllegalArgumentException("Unknow ViewModel class")
        }

    }
}