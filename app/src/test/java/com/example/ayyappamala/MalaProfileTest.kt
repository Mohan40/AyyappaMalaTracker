package com.example.ayyappamala

import com.example.ayyappamala.data.MalaProfile
import org.junit.Assert.assertEquals
import org.junit.Test

class MalaProfileTest {

    @Test
    fun testDefaultProfileValues() {
        val profile = MalaProfile()
        assertEquals(41, profile.totalDays)
        assertEquals("Swami Ayyappa Devotee", profile.swamyName)
        assertEquals("Mala #1", profile.malaNumber)
    }

    @Test
    fun testCustomProfileValues() {
        val profile = MalaProfile(
            startDateEpoch = 123456789L,
            totalDays = 21,
            swamyName = "Ananda Swamy",
            malaNumber = "Mala #2"
        )
        assertEquals(123456789L, profile.startDateEpoch)
        assertEquals(21, profile.totalDays)
        assertEquals("Ananda Swamy", profile.swamyName)
        assertEquals("Mala #2", profile.malaNumber)
    }
}
