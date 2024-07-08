package com.example.docenteapp.models

data class Attendance(
    val id: Int,
    val studentId: Int,
    val date: String,
    val present: Boolean
)