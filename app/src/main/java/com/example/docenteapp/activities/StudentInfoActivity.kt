package com.example.docenteapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docenteapp.R
import com.example.docenteapp.adapters.StudentAdapter
import com.example.docenteapp.database.DatabaseHelper
import com.example.docenteapp.models.Student

class StudentInfoActivity : AppCompatActivity() {

    private lateinit var rvStudents: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_info)

        rvStudents = findViewById(R.id.rvStudents)
        dbHelper = DatabaseHelper(this)

        loadStudents()
    }

    private fun loadStudents() {
        val students = getStudents()
        adapter = StudentAdapter(students)
        rvStudents.adapter = adapter
        rvStudents.layoutManager = LinearLayoutManager(this)
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
}