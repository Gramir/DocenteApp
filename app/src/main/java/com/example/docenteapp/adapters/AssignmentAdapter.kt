package com.example.docenteapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docenteapp.R
import com.example.docenteapp.models.Assignment

class AssignmentAdapter(private val assignments: List<Assignment>) :
    RecyclerView.Adapter<AssignmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDueDate: TextView = view.findViewById(R.id.tvDueDate)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_assignment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val assignment = assignments[position]
        holder.tvTitle.text = assignment.title
        holder.tvDueDate.text = "Fecha límite: ${assignment.dueDate}"
        holder.tvStatus.text = "Estado: ${assignment.status}"
    }

    override fun getItemCount() = assignments.size
}