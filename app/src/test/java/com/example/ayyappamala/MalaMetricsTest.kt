package com.example.ayyappamala

import com.example.ayyappamala.data.DailyLog
import com.example.ayyappamala.data.getTodayDateKey
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MalaMetricsTest {

    @Test
    fun testTodayDateKeyFormat() {
        val dateKey = getTodayDateKey()
        assertNotNull(dateKey)
        // Format should be yyyy-MM-dd (length 10)
        assertEquals(10, dateKey.length)
        assertEquals('-', dateKey[4])
        assertEquals('-', dateKey[7])
    }

    @Test
    fun testDailyLogTaskCountsAndNames() {
        val log = DailyLog(
            dateKey = "2026-01-01",
            morningBath = true,
            eveningBath = true,
            malaWorn = true
        )
        assertEquals(3, log.completedTasksCount())
        val names = log.completedTaskNames()
        assertEquals(3, names.size)
        assertEquals("Morning Bath", names[0])
        assertEquals("Evening Bath", names[1])
        assertEquals("Mala Worn", names[2])
    }
}
