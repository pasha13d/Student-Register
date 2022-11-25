package com.example.roomdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.databinding.StudentListsBinding
import com.example.roomdb.db.Student

class StudentRecyclerViewAdapter(
    private val clickListener:(Student)->Unit
):RecyclerView.Adapter<StudentViewHolder>() {
    private val studentList = ArrayList<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val listItem = layoutInflater.inflate(R.layout.student_lists, parent, false)

        val binding = StudentListsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun setList(students: List<Student>) {
        studentList.clear()
        studentList.addAll(students)
    }

}

class StudentViewHolder(private val binding: StudentListsBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(student: Student, clickListener:(Student)->Unit){
        binding.apply {
//            val nameTextView = view.findViewById<TextView>(R.id.tvName)
//            val emailTextView = view.findViewById<TextView>(R.id.tvEmail)
            tvName.text = student.name
            tvEmail.text = student.email

            root.setOnClickListener {
                clickListener(student)
            }
        }
    }
}