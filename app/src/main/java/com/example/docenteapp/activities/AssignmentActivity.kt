package com.example.docenteapp.activities

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docenteapp.R
import com.example.docenteapp.adapters.AssignmentAdapter
import com.example.docenteapp.database.DatabaseHelper
import com.example.docenteapp.models.Assignment

class AssignmentActivity : AppCompatActivity() {

    private lateinit var rvAssignments: RecyclerView
    private lateinit var btnAddAssignment: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: AssignmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment)

        rvAssignments = findViewById(R.id.rvAssignments)
        btnAddAssignment = findViewById(R.id.btnAddAssignment)
        dbHelper = DatabaseHelper(this)

        loadAssignments()

        btnAddAssignment.setOnClickListener {
            showAddAssignmentDialog()
        }
    }

    private fun loadAssignments() {
        val assignments = getAssignments()
        adapter = AssignmentAdapter(assignments)
        rvAssignments.adapter = adapter
        rvAssignments.layoutManager = LinearLayoutManager(this)
    }

    private fun getAssignments(): List<Assignment> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "assignments",
            null,
            null,
            null,
            null,
            null,
            "due_date ASC"
        )

        val assignments = mutableListOf<Assignment>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val title = getString(getColumnIndexOrThrow("title"))
                val description = getString(getColumnIndexOrThrow("description"))
                val dueDate = getString(getColumnIndexOrThrow("due_date"))
                val status = getString(getColumnIndexOrThrow("status"))

                assignments.add(Assignment(id, title, description, dueDate, status))
            }
        }
        cursor.close()
        return assignments
    }

    private fun showAddAssignmentDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_assignment, null)
        val etTitle = dialogLayout.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogLayout.findViewById<EditText>(R.id.etDescription)
        val etDueDate = dialogLayout.findViewById<EditText>(R.id.etDueDate)

        builder.setView(dialogLayout)
        builder.setTitle("Agregar Asignación")
        builder.setPositiveButton("Agregar") { dialog, which ->
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            val dueDate = etDueDate.text.toString()

            if (title.isNotEmpty() && dueDate.isNotEmpty()) {
                addAssignment(title, description, dueDate)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> }

        val dialog = builder.create()
        dialog.show()
    }

    private fun addAssignment(title: String, description: String, dueDate: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("description", description)
            put("due_date", dueDate)
            put("status", "Pendiente")
        }

        val newRowId = db.insert("assignments", null, values)
        if (newRowId != -1L) {
            Toast.makeText(this, "Asignación agregada con éxito", Toast.LENGTH_SHORT).show()
            loadAssignments()
            com.example.docenteapp.utils.NotificationUtil.sendNotificationToStudents(this, "Nueva asignación: $title")
        } else {
            Toast.makeText(this, "Error al agregar la asignación", Toast.LENGTH_SHORT).show()
        }
    }
}