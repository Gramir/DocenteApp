package com.example.docenteapp.models

data class Assignment(
    val id: Int,
    val title: String,
    val description: String?,
    val dueDate: String,
    val status: String
)