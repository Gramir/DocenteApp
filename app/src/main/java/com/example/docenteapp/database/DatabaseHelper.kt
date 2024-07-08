package com.example.docenteapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.docenteapp.utils.TestDataHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "DocenteApp.db"
        private const val DATABASE_VERSION = 1

        // Tablas
        private const val TABLE_USERS = "users"
        private const val TABLE_STUDENTS = "students"
        private const val TABLE_ATTENDANCE = "attendance"
        private const val TABLE_ASSIGNMENTS = "assignments"
        private const val TABLE_SUBJECTS = "subjects"
        private const val TABLE_COURSES = "courses"

        // Columnas comunes
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"

        // Columnas específicas
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_LAST_NAME = "last_name"
        private const val COLUMN_BIRTH_DATE = "birth_date"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_STUDENT_ID = "student_id"
        private const val COLUMN_PRESENT = "present"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DUE_DATE = "due_date"
        private const val COLUMN_STATUS = "status"
        private const val COLUMN_SUBJECT_ID = "subject_id"
        private const val COLUMN_COURSE_ID = "course_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla de usuarios (docentes)
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUsersTable)

        // Crear tabla de estudiantes
        val createStudentsTable = """
            CREATE TABLE $TABLE_STUDENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_LAST_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_PHONE TEXT,
                $COLUMN_BIRTH_DATE TEXT
            )
        """.trimIndent()
        db.execSQL(createStudentsTable)

        // Crear tabla de asistencia
        val createAttendanceTable = """
            CREATE TABLE $TABLE_ATTENDANCE (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_STUDENT_ID INTEGER,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_PRESENT INTEGER NOT NULL,
                FOREIGN KEY($COLUMN_STUDENT_ID) REFERENCES $TABLE_STUDENTS($COLUMN_ID)
            )
        """.trimIndent()
        db.execSQL(createAttendanceTable)

        // Crear tabla de asignaciones
        val createAssignmentsTable = """
            CREATE TABLE $TABLE_ASSIGNMENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_DUE_DATE TEXT NOT NULL,
                $COLUMN_STATUS TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createAssignmentsTable)

        // Crear tabla de asignaturas
        val createSubjectsTable = """
            CREATE TABLE $TABLE_SUBJECTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createSubjectsTable)

        // Crear tabla de cursos
        val createCoursesTable = """
            CREATE TABLE $TABLE_COURSES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createCoursesTable)


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // En caso de actualización de la base de datos, podríamos manejar las migraciones aquí
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ATTENDANCE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ASSIGNMENTS")
        onCreate(db)
    }
}