package com.example.ayyappamala.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MalaProfile(
    val startDateEpoch: Long = System.currentTimeMillis(),
    val totalDays: Int = 41,
    val swamyName: String = "Swami Ayyappa Devotee",
    val malaNumber: String = "Mala #1",
    val autoBackupEnabled: Boolean = false,
    val lastAutoBackupDate: String = ""
)

data class DailyLog(
    val dateKey: String, // format: yyyy-MM-dd
    val morningBath: Boolean = false,
    val eveningBath: Boolean = false,
    val malaWorn: Boolean = false,
    val morningPrayer: Boolean = false,
    val eveningPrayer: Boolean = false,
    val sattvicDiet: Boolean = false,
    val celibacy: Boolean = false,
    val truthAndPatience: Boolean = false,
    val templeVisit: Boolean = false,
    val notes: String = ""
) {
    fun completedTasksCount(): Int {
        var count = 0
        if (morningBath) count++
        if (eveningBath) count++
        if (malaWorn) count++
        if (morningPrayer) count++
        if (eveningPrayer) count++
        if (sattvicDiet) count++
        if (celibacy) count++
        if (truthAndPatience) count++
        if (templeVisit) count++
        return count
    }

    fun completedTaskNames(): List<String> {
        val list = mutableListOf<String>()
        if (morningBath) list.add("Morning Bath")
        if (eveningBath) list.add("Evening Bath")
        if (malaWorn) list.add("Mala Worn")
        if (morningPrayer) list.add("Morning Prayer")
        if (eveningPrayer) list.add("Evening Prayer")
        if (sattvicDiet) list.add("Sattvic Diet")
        if (celibacy) list.add("Brahmacharya")
        if (truthAndPatience) list.add("Truth & Patience")
        if (templeVisit) list.add("Temple Visit")
        return list
    }

    companion object {
        const val totalTaskCount: Int = 9
    }
}

data class MalaExportData(
    val profile: MalaProfile,
    val logs: List<DailyLog>
)

fun getTodayDateKey(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}
