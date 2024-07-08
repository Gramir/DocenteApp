package com.example.docenteapp.activities

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docenteapp.R
import com.example.docenteapp.adapters.AttendanceAdapter
import com.example.docenteapp.database.DatabaseHelper
import com.example.docenteapp.models.Student
import java.text.SimpleDateFormat
import java.util.*

class AttendanceActivity : AppCompatActivity() {

    private lateinit var tvDate: TextView
    private lateinit var rvAttendance: RecyclerView
    private lateinit var btnSaveAttendance: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: AttendanceAdapter
    private lateinit var currentDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        tvDate = findViewById(R.id.tvDate)
        rvAttendance = findViewById(R.id.rvAttendance)
        btnSaveAttendance = findViewById(R.id.btnSaveAttendance)
        dbHelper = DatabaseHelper(this)

        currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        tvDate.text = "Fecha: $currentDate"

        val students = getStudents()
        adapter = AttendanceAdapter(students, getExistingAttendance(currentDate, students))
        rvAttendance.adapter = adapter
        rvAttendance.layoutManager = LinearLayoutManager(this)

        btnSaveAttendance.setOnClickListener {
            saveAttendance()
        }
    }

    private fun getStudents(): List<Student> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "students",
            null,
            null,
            null,
            null,
            null,
            "last_name ASC"
        )

        val students = mutableListOf<Student>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow("name"))
                val lastName = getString(getColumnIndexOrThrow("last_name"))
                val email = getString(getColumnIndexOrThrow("email"))
                val phone = getString(getColumnIndexOrThrow("phone"))
                val birthDate = getString(getColumnIndexOrThrow("birth_date"))

                students.add(Student(id, name, lastName, email, phone, birthDate))
            }
        }
        cursor.close()
        return students
    }

    private fun getExistingAttendance(date: String, students: List<Student>): Map<Int, Boolean> {
        val db = dbHelper.readableDatabase
        val attendanceMap = mutableMapOf<Int, Boolean>()

        students.forEach { student ->
            val cursor = db.query(
                "attendance",
                arrayOf("present"),
                "student_id = ? AND date = ?",
                arrayOf(student.id.toString(), date),
                null,
                null,
                null
            )

            if (cursor.moveToFirst()) {
                attendanceMap[student.id] = cursor.getInt(0) == 1
            }
            cursor.close()
        }

        return attendanceMap
    }

    private fun saveAttendance() {
        val db = dbHelper.writableDatabase
        val attendanceMap = adapter.getAttendanceMap()

        db.beginTransaction()
        try {
            // Primero, eliminamos las asistencias existentes para la fecha actual
            db.delete("attendance", "date = ?", arrayOf(currentDate))

            // Luego, insertamos las nuevas asistencias
            for ((studentId, present) in attendanceMap) {
                val values = ContentValues().apply {
                    put("student_id", studentId)
                    put("date", currentDate)
                    put("present", if (present) 1 else 0)
                }
                db.insert("attendance", null, values)
            }
            db.setTransactionSuccessful()
            Toast.makeText(this, "Asistencia guardada con éxito", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar la asistencia: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            db.endTransaction()
        }
    }
}