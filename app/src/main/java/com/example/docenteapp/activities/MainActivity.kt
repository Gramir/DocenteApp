package com.example.docenteapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.docenteapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnAttendance: Button
    private lateinit var btnAssignments: Button
    private lateinit var btnStudentInfo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAttendance = findViewById(R.id.btnAttendance)
        btnAssignments = findViewById(R.id.btnAssignments)
        btnStudentInfo = findViewById(R.id.btnStudentInfo)

        btnAttendance.setOnClickListener {
            val intent = Intent(this, AttendanceActivity::class.java)
            startActivity(intent)
        }

        btnAssignments.setOnClickListener {
            val intent = Intent(this, AssignmentActivity::class.java)
            startActivity(intent)
        }

        btnStudentInfo.setOnClickListener {
            val intent = Intent(this, StudentInfoActivity::class.java)
            startActivity(intent)
        }
    }
}