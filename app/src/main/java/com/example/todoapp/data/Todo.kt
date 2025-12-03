package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val isCompleted: Boolean = false,
    val createdAt: String = getCurrentDateTimeString(),
    val updatedAt: String = getCurrentDateTimeString()
) {
    // Format tanggal ke format Indonesia "03 Desember 2025, 14:30:45.123"
    fun getFormattedCreatedDate(): String {
        return formatDbDateTimeToIndonesian(createdAt)
    }

    fun getFormattedUpdatedDate(): String {
        return formatDbDateTimeToIndonesian(updatedAt)
    }

    // Format ringkas untuk UI "03 Des 2025, 14:30"
    fun getCompactFormattedCreatedDate(): String {
        return formatDbDateTimeToCompact(createdAt)
    }

    fun getCompactFormattedUpdatedDate(): String {
        return formatDbDateTimeToCompact(updatedAt)
    }

    companion object {
        private val monthNames = arrayOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )

        private val monthNamesShort = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "Mei", "Jun",
            "Jul", "Ags", "Sep", "Okt", "Nov", "Des"
        )

        // Get current date and time in "YYYY-MM-DD HH:mm:ss.SSS" format for database
        fun getCurrentDateTimeString(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
            return sdf.format(Date())
        }

        // Convert database datetime string "2025-12-03 14:30:45.123" to Indonesian format "03 Desember 2025, 14:30:45.123"
        fun formatDbDateTimeToIndonesian(dbDateTimeString: String): String {
            return try {
                // Split date and time parts
                val dateTimeParts = dbDateTimeString.split(" ")
                if (dateTimeParts.size >= 2) {
                    val datePart = dateTimeParts[0] // "2025-12-03"
                    val timePart = dateTimeParts[1] // "14:30:45.123"

                    // Parse date part
                    val dateParts = datePart.split("-")
                    if (dateParts.size == 3) {
                        val year = dateParts[0]
                        val monthIndex = dateParts[1].toInt() - 1
                        val day = dateParts[2].toInt()

                        val month = if (monthIndex in 0..11) monthNames[monthIndex] else "Invalid"

                        // Format: "03 Desember 2025, 14:30:45.123"
                        String.format(Locale.getDefault(), "%02d %s %s, %s", day, month, year, timePart)
                    } else {
                        dbDateTimeString // Return as-is if date parsing fails
                    }
                } else {
                    dbDateTimeString // Return as-is if splitting fails
                }
            } catch (e: Exception) {
                dbDateTimeString // Return as-is if parsing fails
            }
        }

        // Convert database datetime string "2025-12-03 14:30:45.123" to compact format "03 Des 2025, 14:30"
        fun formatDbDateTimeToCompact(dbDateTimeString: String): String {
            return try {
                // Split date and time parts
                val dateTimeParts = dbDateTimeString.split(" ")
                if (dateTimeParts.size >= 2) {
                    val datePart = dateTimeParts[0] // "2025-12-03"
                    val timePart = dateTimeParts[1] // "14:30:45.123"

                    // Parse date part
                    val dateParts = datePart.split("-")
                    if (dateParts.size == 3) {
                        val year = dateParts[0]
                        val monthIndex = dateParts[1].toInt() - 1
                        val day = dateParts[2].toInt()

                        val month = if (monthIndex in 0..11) monthNamesShort[monthIndex] else "Invalid"

                        // Extract hour and minute only from time
                        val timeOnly = timePart.substring(0, 5) // "14:30"

                        // Format: "03 Des 2025, 14:30"
                        String.format(Locale.getDefault(), "%02d %s %s, %s", day, month, year, timeOnly)
                    } else {
                        dbDateTimeString // Return as-is if date parsing fails
                    }
                } else {
                    dbDateTimeString // Return as-is if splitting fails
                }
            } catch (e: Exception) {
                dbDateTimeString // Return as-is if parsing fails
            }
        }
    }
}
