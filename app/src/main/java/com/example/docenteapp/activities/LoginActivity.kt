package com.example.docenteapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.docenteapp.R
import com.example.docenteapp.database.DatabaseHelper
import com.example.docenteapp.utils.TestDataHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar dbHelper
        dbHelper = DatabaseHelper(this)

        // Insertar datos de prueba si es necesario
        TestDataHelper.insertTestDataIfNeeded(dbHelper)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (validateUser(email, password)) {
                    // Inicio de sesión exitoso
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUser(email: String, password: String): Boolean {
        val db = dbHelper.readableDatabase
        val projection = arrayOf("id")
        val selection = "email = ? AND password = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(
            "users",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }
}