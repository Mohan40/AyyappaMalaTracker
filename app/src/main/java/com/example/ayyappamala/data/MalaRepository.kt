package com.example.ayyappamala.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class MalaRepository(private val context: Context) {
    private val file = File(context.filesDir, "ayyappa_mala_data.json")

    fun loadData(): MalaExportData {
        if (!file.exists()) {
            return MalaExportData(MalaProfile(), emptyList())
        }
        try {
            val jsonString = file.readText()
            val root = JSONObject(jsonString)
            
            val profileObj = root.optJSONObject("profile")
            val profile = if (profileObj != null) {
                MalaProfile(
                    startDateEpoch = profileObj.optLong("startDateEpoch", System.currentTimeMillis()),
                    totalDays = profileObj.optInt("totalDays", 41),
                    swamyName = profileObj.optString("swamyName", "Swami Ayyappa Devotee"),
                    malaNumber = profileObj.optString("malaNumber", "Mala #1")
                )
            } else {
                MalaProfile()
            }

            val logsArray = root.optJSONArray("logs")
            val logs = mutableListOf<DailyLog>()
            if (logsArray != null) {
                for (i in 0 until logsArray.length()) {
                    val obj = logsArray.getJSONObject(i)
                    logs.add(
                        DailyLog(
                            dateKey = obj.optString("dateKey", ""),
                            morningBath = obj.optBoolean("morningBath", false),
                            eveningBath = obj.optBoolean("eveningBath", false),
                            malaWorn = obj.optBoolean("malaWorn", false),
                            morningPrayer = obj.optBoolean("morningPrayer", false),
                            eveningPrayer = obj.optBoolean("eveningPrayer", false),
                            sattvicDiet = obj.optBoolean("sattvicDiet", false),
                            celibacy = obj.optBoolean("celibacy", false),
                            truthAndPatience = obj.optBoolean("truthAndPatience", false),
                            templeVisit = obj.optBoolean("templeVisit", false),
                            notes = obj.optString("notes", "")
                        )
                    )
                }
            }

            return MalaExportData(profile, logs)
        } catch (e: Exception) {
            e.printStackTrace()
            return MalaExportData(MalaProfile(), emptyList())
        }
    }

    fun saveData(data: MalaExportData) {
        try {
            val root = JSONObject()
            val profileObj = JSONObject().apply {
                put("startDateEpoch", data.profile.startDateEpoch)
                put("totalDays", data.profile.totalDays)
                put("swamyName", data.profile.swamyName)
                put("malaNumber", data.profile.malaNumber)
            }
            root.put("profile", profileObj)

            val logsArray = JSONArray()
            for (log in data.logs) {
                val obj = JSONObject().apply {
                    put("dateKey", log.dateKey)
                    put("morningBath", log.morningBath)
                    put("eveningBath", log.eveningBath)
                    put("malaWorn", log.malaWorn)
                    put("morningPrayer", log.morningPrayer)
                    put("eveningPrayer", log.eveningPrayer)
                    put("sattvicDiet", log.sattvicDiet)
                    put("celibacy", log.celibacy)
                    put("truthAndPatience", log.truthAndPatience)
                    put("templeVisit", log.templeVisit)
                    put("notes", log.notes)
                }
                logsArray.put(obj)
            }
            root.put("logs", logsArray)

            file.writeText(root.toString(2))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun exportToUri(outputStream: OutputStream): Boolean {
        return try {
            val data = loadData()
            val root = JSONObject()
            val profileObj = JSONObject().apply {
                put("startDateEpoch", data.profile.startDateEpoch)
                put("totalDays", data.profile.totalDays)
                put("swamyName", data.profile.swamyName)
                put("malaNumber", data.profile.malaNumber)
            }
            root.put("profile", profileObj)

            val logsArray = JSONArray()
            for (log in data.logs) {
                val obj = JSONObject().apply {
                    put("dateKey", log.dateKey)
                    put("morningBath", log.morningBath)
                    put("eveningBath", log.eveningBath)
                    put("malaWorn", log.malaWorn)
                    put("morningPrayer", log.morningPrayer)
                    put("eveningPrayer", log.eveningPrayer)
                    put("sattvicDiet", log.sattvicDiet)
                    put("celibacy", log.celibacy)
                    put("truthAndPatience", log.truthAndPatience)
                    put("templeVisit", log.templeVisit)
                    put("notes", log.notes)
                }
                logsArray.put(obj)
            }
            root.put("logs", logsArray)

            outputStream.use { it.write(root.toString(2).toByteArray()) }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun importFromUri(inputStream: InputStream): Boolean {
        return try {
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val root = JSONObject(jsonString)
            if (root.has("profile") || root.has("logs")) {
                file.writeText(root.toString(2))
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
