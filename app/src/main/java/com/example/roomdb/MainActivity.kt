package com.example.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.db.Student
import com.example.roomdb.db.StudentDB

class MainActivity : AppCompatActivity() {
    private lateinit var nameEditTest: EditText
    private lateinit var emailEditTest: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    private lateinit var viewModel: StudentViewModel
    // defining recyclerview
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditTest = findViewById(R.id.etName)
        emailEditTest = findViewById(R.id.etEmail)
        saveButton = findViewById(R.id.btnSave)
        clearButton = findViewById(R.id.btnClear)

        studentRecyclerView = findViewById(R.id.rvStudents)

        val dao = StudentDB.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        saveButton.setOnClickListener {
            saveStudentData()
            clearInput()
        }
        clearButton.setOnClickListener {
            clearInput()
        }

        initRecyclerView()
    }

    private fun saveStudentData() {
        val name = nameEditTest.text.toString()
        val email = emailEditTest.text.toString()
        val student = Student(0, name, email)

        viewModel.insertStudent(student)
    }

    private fun clearInput() {
        nameEditTest.setText("")
        emailEditTest.setText("")
    }

    // display list of student
    private fun initRecyclerView() {
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewAdapter{
            selectedItem: Student -> listItemClicked(selectedItem)
        }
        studentRecyclerView.adapter = adapter

        displayStudentList()
    }

    private fun displayStudentList(){
        viewModel.students.observe(this,{
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(student: Student){
        Toast.makeText(
            this,
            "Student name is ${student.name}",
            Toast.LENGTH_LONG
        ).show()
    }
}