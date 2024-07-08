package com.example.docenteapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docenteapp.R
import com.example.docenteapp.models.Student

class AttendanceAdapter(
    private val students: List<Student>,
    private val existingAttendance: Map<Int, Boolean>
) : RecyclerView.Adapter<AttendanceAdapter.ViewHolder>() {

    private val attendanceMap = existingAttendance.toMutableMap()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvStudentName: TextView = view.findViewById(R.id.tvStudentName)
        val cbPresent: CheckBox = view.findViewById(R.id.cbPresent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attendance, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.tvStudentName.text = "${student.name} ${student.lastName}"
        holder.cbPresent.isChecked = attendanceMap[student.id] ?: false
        holder.cbPresent.setOnCheckedChangeListener { _, isChecked ->
            attendanceMap[student.id] = isChecked
        }
    }

    override fun getItemCount() = students.size

    fun getAttendanceMap(): Map<Int, Boolean> = attendanceMap
}