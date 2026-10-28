package com.example.ayyappamala.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ayyappamala.data.DailyLog
import com.example.ayyappamala.data.MalaExportData
import com.example.ayyappamala.data.MalaProfile
import com.example.ayyappamala.data.MalaRepository
import com.example.ayyappamala.data.getTodayDateKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MalaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MalaRepository(application)

    private val _profile = MutableStateFlow(MalaProfile())
    val profile: StateFlow<MalaProfile> = _profile.asStateFlow()

    private val _dailyLogs = MutableStateFlow<List<DailyLog>>(emptyList())
    val dailyLogs: StateFlow<List<DailyLog>> = _dailyLogs.asStateFlow()

    private val _todayLog = MutableStateFlow(DailyLog(dateKey = getTodayDateKey()))
    val todayLog: StateFlow<DailyLog> = _todayLog.asStateFlow()

    private val _currentDayNumber = MutableStateFlow(1)
    val currentDayNumber: StateFlow<Int> = _currentDayNumber.asStateFlow()

    private val _streakCount = MutableStateFlow(0)
    val streakCount: StateFlow<Int> = _streakCount.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val data = repository.loadData()
            _profile.value = data.profile
            _dailyLogs.value = data.logs
            
            val todayKey = getTodayDateKey()
            val existingToday = data.logs.find { it.dateKey == todayKey } ?: DailyLog(dateKey = todayKey)
            _todayLog.value = existingToday

            calculateMetrics(data.profile, data.logs)
        }
    }

    private fun calculateMetrics(profile: MalaProfile, logs: List<DailyLog>) {
        // Calculate current day number based on start date
        val startCal = Calendar.getInstance().apply {
            timeInMillis = profile.startDateEpoch
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val todayCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val diffMillis = todayCal.timeInMillis - startCal.timeInMillis
        val diffDays = TimeUnit.MILLISECONDS.toDays(diffMillis).toInt() + 1
        _currentDayNumber.value = maxOf(1, minOf(profile.totalDays, diffDays))

        // Calculate streak (consecutive days with >= 5 tasks completed)
        var streak = 0
        val sortedLogs = logs.sortedByDescending { it.dateKey }
        for (log in sortedLogs) {
            if (log.completedTasksCount() >= 5) {
                streak++
            } else {
                break
            }
        }
        _streakCount.value = streak
    }

    fun updateTodayLog(updater: (DailyLog) -> DailyLog) {
        viewModelScope.launch {
            val updated = updater(_todayLog.value)
            _todayLog.value = updated

            val currentLogs = _dailyLogs.value.toMutableList()
            val index = currentLogs.indexOfFirst { it.dateKey == updated.dateKey }
            if (index >= 0) {
                currentLogs[index] = updated
            } else {
                currentLogs.add(updated)
            }
            _dailyLogs.value = currentLogs

            repository.saveData(MalaExportData(_profile.value, currentLogs))
            calculateMetrics(_profile.value, currentLogs)
        }
    }

    fun updateProfile(newProfile: MalaProfile) {
        viewModelScope.launch {
            _profile.value = newProfile
            repository.saveData(MalaExportData(newProfile, _dailyLogs.value))
            calculateMetrics(newProfile, _dailyLogs.value)
        }
    }

    fun exportData(outputStream: OutputStream): Boolean {
        return repository.exportToUri(outputStream)
    }

    fun importData(inputStream: InputStream): Boolean {
        val success = repository.importFromUri(inputStream)
        if (success) {
            loadData()
        }
        return success
    }

    fun resetData() {
        viewModelScope.launch {
            val freshProfile = MalaProfile()
            val emptyLogs = emptyList<DailyLog>()
            _profile.value = freshProfile
            _dailyLogs.value = emptyLogs
            _todayLog.value = DailyLog(dateKey = getTodayDateKey())
            repository.saveData(MalaExportData(freshProfile, emptyLogs))
            calculateMetrics(freshProfile, emptyLogs)
        }
    }
}
