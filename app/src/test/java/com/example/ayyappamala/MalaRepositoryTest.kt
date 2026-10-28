package com.example.ayyappamala

import com.example.ayyappamala.data.DailyLog
import com.example.ayyappamala.data.MalaExportData
import com.example.ayyappamala.data.MalaProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MalaRepositoryTest {

    @Test
    fun testMalaExportDataStructure() {
        val profile = MalaProfile(swamyName = "Test Swamy", totalDays = 41)
        val logs = listOf(
            DailyLog(dateKey = "2026-01-01", morningBath = true)
        )
        val exportData = MalaExportData(profile, logs)

        assertEquals("Test Swamy", exportData.profile.swamyName)
        assertEquals(41, exportData.profile.totalDays)
        assertEquals(1, exportData.logs.size)
        assertEquals("2026-01-01", exportData.logs[0].dateKey)
        assert(exportData.logs[0].morningBath)
    }
}
