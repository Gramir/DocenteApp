package com.example.docenteapp.utils

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.docenteapp.database.DatabaseHelper

object TestDataHelper {

    fun insertTestDataIfNeeded(dbHelper: DatabaseHelper) {
        val db = dbHelper.writableDatabase

        // Insertar datos de prueba solo si la base de datos está vacía
        if (isDatabaseEmpty(db)) {
            insertTeachers(db)
            insertStudents(db)
            insertSubjects(db)
            insertCourses(db)
            insertAssignments(db)
        }
    }

    private fun isDatabaseEmpty(db: SQLiteDatabase): Boolean {
        val cursor = db.rawQuery("SELECT COUNT(*) FROM users", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count == 0
    }

    private fun insertTeachers(db: SQLiteDatabase) {
        val teachers = arrayOf(
            arrayOf("Juan Pérez", "juan.perez@ejemplo.com", "password123"),
            arrayOf("Pedro López", "test@test.com", "123456")
        )

        teachers.forEach { teacher ->
            val values = ContentValues().apply {
                put("name", teacher[0])
                put("email", teacher[1])
                put("password", teacher[2])
            }
            db.insert("users", null, values)
        }
    }

    private fun insertStudents(db: SQLiteDatabase) {
        val students = arrayOf(
            arrayOf("Ana", "López", "ana.lopez@ejemplo.com", "123456789", "2000-05-15"),
            arrayOf("Carlos", "Martínez", "carlos.martinez@ejemplo.com", "987654321", "2001-02-20"),
            arrayOf("Laura", "Sánchez", "laura.sanchez@ejemplo.com", "456789123", "2000-11-10")
        )

        students.forEach { student ->
            val values = ContentValues().apply {
                put("name", student[0])
                put("last_name", student[1])
                put("email", student[2])
                put("phone", student[3])
                put("birth_date", student[4])
            }
            db.insert("students", null, values)
        }
    }

    private fun insertSubjects(db: SQLiteDatabase) {
        val subjects = arrayOf("Matemáticas", "Lenguaje", "Ciencias", "Historia")

        subjects.forEach { subject ->
            val values = ContentValues().apply {
                put("name", subject)
            }
            db.insert("subjects", null, values)
        }
    }

    private fun insertCourses(db: SQLiteDatabase) {
        val courses = arrayOf("1º A", "1º B", "2º A", "2º B")

        courses.forEach { course ->
            val values = ContentValues().apply {
                put("name", course)
            }
            db.insert("courses", null, values)
        }
    }

    private fun insertAssignments(db: SQLiteDatabase) {
        val assignments = arrayOf(
            arrayOf("Tarea de Matemáticas", "Resolver problemas del capítulo 3", "2023-07-15", "Pendiente"),
            arrayOf("Ensayo de Historia", "Escribir sobre la Revolución Francesa", "2023-07-20", "Pendiente")
        )

        assignments.forEach { assignment ->
            val values = ContentValues().apply {
                put("title", assignment[0])
                put("description", assignment[1])
                put("due_date", assignment[2])
                put("status", assignment[3])
            }
            db.insert("assignments", null, values)
        }
    }
}