package com.example.todoapp.documentation

/**
 * DATABASE STRING DATE FORMAT SUMMARY - File Documentation 7
 *
 * Status: DATABASE SCHEMA BERHASIL DIUBAH
 * Created: December 3, 2025
 *
 * PERUBAHAN UTAMA:
 *
 * ❌ SEBELUM (Long timestamp):
 * ```kotlin
 * val createdAt: Long = System.currentTimeMillis()
 * val updatedAt: Long = System.currentTimeMillis()
 * ```
 * - Database menyimpan: 1733270400000 (milliseconds)
 * - Perlu konversi timestamp ke tanggal
 *
 * ✅ SESUDAH (String date):
 * ```kotlin
 * val createdAt: String = getCurrentDateString()
 * val updatedAt: String = getCurrentDateString()
 * ```
 * - Database menyimpan: "2025-12-03" (YYYY-MM-DD format)
 * - Lebih readable dan human-friendly
 *
 * DETAIL IMPLEMENTASI:
 *
 * 1. Todo Entity Update:
 * ```kotlin
 * @Entity(tableName = "todos")
 * data class Todo(
 *     @PrimaryKey(autoGenerate = true)
 *     val id: Int = 0,
 *     val title: String,
 *     val isCompleted: Boolean = false,
 *     val createdAt: String = getCurrentDateString(),  // ← String format
 *     val updatedAt: String = getCurrentDateString()   // ← String format
 * )
 * ```
 *
 * 2. Helper Functions:
 * ```kotlin
 * companion object {
 *     // Generate current date string "2025-12-03"
 *     fun getCurrentDateString(): String {
 *         val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
 *         return sdf.format(Date())
 *     }
 *
 *     // Convert "2025-12-03" → "03 Desember 2025"
 *     fun formatDbDateToIndonesian(dbDateString: String): String {
 *         val parts = dbDateString.split("-")
 *         val year = parts[0]
 *         val monthIndex = parts[1].toInt() - 1
 *         val day = parts[2].toInt()
 *         val month = monthNames[monthIndex]
 *         return String.format("%02d %s %s", day, month, year)
 *     }
 * }
 * ```
 *
 * 3. DAO Query Update:
 * ```kotlin
 * @Query("UPDATE todos SET isCompleted = :isCompleted, updatedAt = :updatedAt WHERE id = :todoId")
 * suspend fun updateTodoCompleted(todoId: Int, isCompleted: Boolean, updatedAt: String)
 * ```
 *
 * 4. Repository Update:
 * ```kotlin
 * suspend fun updateTodoCompleted(todoId: Int, isCompleted: Boolean) =
 *     todoDao.updateTodoCompleted(todoId, isCompleted, Todo.getCurrentDateString())
 * ```
 *
 * 5. Database Version:
 * ```kotlin
 * @Database(entities = [Todo::class], version = 3)  // Incremented to v3
 * ```
 *
 * DATABASE COMPARISON:
 *
 * Format di Database:
 * | Field     | Old Format (Long) | New Format (String) |
 * |-----------|-------------------|---------------------|
 * | createdAt | 1733270400000     | "2025-12-03"        |
 * | updatedAt | 1733270400000     | "2025-12-03"        |
 *
 * Display di UI:
 * | Status         | Displayed Text               |
 * |----------------|------------------------------|
 * | Todo Baru      | "Dibuat: 03 Desember 2025"  |
 * | Todo Completed | "Selesai: 03 Desember 2025" |
 *
 * KEUNTUNGAN PERUBAHAN:
 *
 * 🔍 Database Readability:
 * - Before: 1733270400000 (tidak readable)
 * - After: "2025-12-03" (jelas terbaca)
 *
 * 🛠️ Development Debugging:
 * - Mudah debug query SQL
 * - Bisa langsung lihat tanggal di database viewer
 * - Format standar ISO 8601
 *
 * 📈 Performance:
 * - String comparison lebih mudah untuk sorting by date
 * - Tidak perlu konversi timestamp berulang kali
 *
 * 🔧 Maintenance:
 * - Lebih mudah untuk query berdasarkan tanggal
 * - Compatible dengan SQL date functions
 *
 * CONTOH USAGE:
 *
 * Database Storage:
 * ```sql
 * INSERT INTO todos (title, createdAt, updatedAt)
 * VALUES ('Beli groceries', '2025-12-03', '2025-12-03');
 *
 * UPDATE todos SET isCompleted = 1, updatedAt = '2025-12-03' WHERE id = 1;
 * ```
 *
 * UI Display:
 * ```
 * ☐ Beli groceries
 *    Dibuat: 03 Desember 2025
 *
 * ☑ ~~Beli groceries~~
 *    Selesai: 03 Desember 2025
 * ```
 *
 * PERUBAHAN BERHASIL! DATABASE SEKARANG MENGGUNAKAN STRING FORMAT "2025-12-03"!
 * Database akan otomatis recreate dengan schema baru, dan semua tanggal akan tersimpan dalam format yang readable!
 */
