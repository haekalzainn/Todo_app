package com.example.todoapp.documentation

/**
 * TODO UPDATE & DATE FORMAT SUMMARY - File Documentation 6
 *
 * Status: TODO UPDATE & TANGGAL FORMAT BERHASIL DITAMBAHKAN
 * Created: December 3, 2025
 *
 * FITUR YANG DITAMBAHKAN:
 *
 * 1. ✅ Field updatedAt di Tabel Todo:
 * - Database Schema: Ditambahkan field updatedAt: Long
 * - Version Update: Database version dinaikkan dari 1 ke 2
 * - Migration Strategy: fallbackToDestructiveMigration() untuk recreate database
 *
 * 2. ✅ Format Tanggal Indonesia:
 * - Format: "03 Desember 2025" (DD MMM YYYY)
 * - Bulan dalam Bahasa Indonesia: Januari, Februari, Maret, dst.
 * - Functions: getFormattedCreatedDate() dan getFormattedUpdatedDate()
 *
 * 3. ✅ Update Otomatis updatedAt:
 * - Trigger: Ketika todo di-mark completed/incomplete
 * - DAO Update: Query sekarang include updatedAt = System.currentTimeMillis()
 * - Repository: Automatically pass timestamp saat update completion
 *
 * 4. ✅ UI Enhancement:
 * - Layout: Todo item sekarang menampilkan tanggal
 * - Dynamic Text: "Dibuat: [tanggal]" atau "Selesai: [tanggal]"
 * - Visual Feedback: Tanggal ikut fade ketika todo completed
 *
 * 5. ✅ EditText Contrast Fix:
 * - Text Color: @color/black untuk kontras tinggi
 * - Background: @color/white dengan border biru
 * - Readability: Teks input sekarang jelas terlihat
 *
 * PERUBAHAN STRUKTUR:
 *
 * Todo Entity:
 * ```kotlin
 * @Entity(tableName = "todos")
 * data class Todo(
 *     @PrimaryKey(autoGenerate = true)
 *     val id: Int = 0,
 *     val title: String,
 *     val isCompleted: Boolean = false,
 *     val createdAt: Long = System.currentTimeMillis(),
 *     val updatedAt: Long = System.currentTimeMillis()  // ← NEW!
 * ) {
 *     fun getFormattedCreatedDate(): String = formatDateToIndonesian(createdAt)
 *     fun getFormattedUpdatedDate(): String = formatDateToIndonesian(updatedAt)
 *
 *     companion object {
 *         fun formatDateToIndonesian(timestamp: Long): String {
 *             // Returns "03 Desember 2025" format
 *         }
 *     }
 * }
 * ```
 *
 * TodoDao Update:
 * ```kotlin
 * @Query("UPDATE todos SET isCompleted = :isCompleted, updatedAt = :updatedAt WHERE id = :todoId")
 * suspend fun updateTodoCompleted(todoId: Int, isCompleted: Boolean, updatedAt: Long)
 * ```
 *
 * Database Migration:
 * ```kotlin
 * @Database(entities = [Todo::class], version = 2)  // Version increased
 * .fallbackToDestructiveMigration()  // Handle schema changes
 * ```
 *
 * UI IMPROVEMENTS:
 *
 * Todo Item Layout:
 * - Before: Hanya title
 * - After: Title + tanggal dalam layout vertikal
 * - Date Format: "Dibuat: 03 Desember 2025" atau "Selesai: 03 Desember 2025"
 *
 * Visual Styling:
 * - Normal State: Title hitam, tanggal abu-abu muda
 * - Completed State: Title strike-through + fade, tanggal juga fade
 * - Responsive: Layout menyesuaikan dengan panjang teks
 *
 * EditText Improvement:
 * - High Contrast: Teks hitam di background putih
 * - Border: Border biru untuk visual clarity
 * - Hint: Tetap biru sesuai tema
 *
 * FLOW UPDATE:
 *
 * Saat Todo Dibuat:
 * 1. createdAt = current timestamp
 * 2. updatedAt = current timestamp
 * 3. Display: "Dibuat: [formatted date]"
 *
 * Saat Todo Di-Check/Uncheck:
 * 1. isCompleted berubah
 * 2. updatedAt = current timestamp (NEW!)
 * 3. Display: "Selesai: [formatted date]" atau kembali ke "Dibuat: [date]"
 *
 * CONTOH OUTPUT:
 *
 * Todo Baru:
 * ```
 * ☐ Beli groceries
 *    Dibuat: 03 Desember 2025
 * ```
 *
 * Todo Selesai:
 * ```
 * ☑ ~~Beli groceries~~
 *    Selesai: 03 Desember 2025
 * ```
 *
 * SEMUA FITUR BERHASIL DITAMBAHKAN & APLIKASI SIAP DIGUNAKAN!
 * Database akan otomatis recreate dengan schema baru, dan semua fungsi tanggal + update tracking sudah bekerja!
 */
