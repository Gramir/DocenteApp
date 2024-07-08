package com.example.docenteapp.models

data class Student(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String?,
    val birthDate: String
)