package com.example.ayyappamala

import com.example.ayyappamala.data.DailyLog
import org.junit.Assert.assertEquals
import org.junit.Test

class DailyLogTest {

    @Test
    fun testEmptyLogCompletedTasksCount() {
        val log = DailyLog(dateKey = "2025-01-01")
        assertEquals(0, log.completedTasksCount())
        assertEquals(0, log.completedTaskNames().size)
    }

    @Test
    fun testPartialLogCompletedTasksCount() {
        val log = DailyLog(
            dateKey = "2025-01-01",
            morningBath = true,
            malaWorn = true,
            sattvicDiet = true
        )
        assertEquals(3, log.completedTasksCount())
        val names = log.completedTaskNames()
        assertEquals(3, names.size)
        assert(names.contains("Morning Bath"))
        assert(names.contains("Mala Worn"))
        assert(names.contains("Sattvic Diet"))
    }

    @Test
    fun testAllTasksCompletedCount() {
        val log = DailyLog(
            dateKey = "2025-01-01",
            morningBath = true,
            eveningBath = true,
            malaWorn = true,
            morningPrayer = true,
            eveningPrayer = true,
            sattvicDiet = true,
            celibacy = true,
            truthAndPatience = true,
            templeVisit = true
        )
        assertEquals(DailyLog.totalTaskCount, log.completedTasksCount())
        assertEquals(9, log.completedTaskNames().size)
    }
}
