package com.example.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.databinding.ActivityMainBinding
import com.example.roomdb.db.Student
import com.example.roomdb.db.StudentDB

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


//    private lateinit var nameEditTest: EditText
//    private lateinit var emailEditTest: EditText
//    private lateinit var saveButton: Button
//    private lateinit var clearButton: Button

    private lateinit var viewModel: StudentViewModel
    // defining recyclerview
//    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerViewAdapter

    // hold selected student
    private lateinit var selectedStudent: Student
    private var isListItemClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        // initialize binding instance
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        nameEditTest = findViewById(R.id.etName)
//        emailEditTest = findViewById(R.id.etEmail)
//        saveButton = findViewById(R.id.btnSave)
//        clearButton = findViewById(R.id.btnClear)

//        studentRecyclerView = findViewById(R.id.rvStudents)

        val dao = StudentDB.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        binding.apply {

            btnSave.setOnClickListener {
                if(isListItemClicked){
                    updateStudentData()
                    clearInput()
                } else {
                    saveStudentData()
                    clearInput()
                }
            }
            btnClear.setOnClickListener {
                if(isListItemClicked){
                    deleteStudentData()
                    clearInput()
                } else {
                    clearInput()
                }

            }
        }

        initRecyclerView()
    }

    private fun saveStudentData() {
        binding.apply {
            Student(
                0,
                etName.text.toString(),
                etEmail.text.toString()
            )
        }
//        val name = nameEditTest.text.toString()
//        val email = emailEditTest.text.toString()
//        val student = Student(0, name, email)
//
//        viewModel.insertStudent(student)
    }

    private fun updateStudentData(){
        binding.apply {
            viewModel.updateStudent(
                Student(
                    selectedStudent.id,
                    etName.text.toString(),
                    etEmail.text.toString(),
                )
            )

            btnSave.text = "Save"
            btnClear.text = "Clear"
            isListItemClicked = false
        }
    }

    private fun deleteStudentData(){
        binding.apply {
            viewModel.deleteStudent(
                Student(
                    selectedStudent.id,
                    etName.text.toString(),
                    etEmail.text.toString(),
                )
            )

            btnSave.text = "Save"
            btnClear.text = "Clear"
            isListItemClicked = false
        }
    }

    private fun clearInput() {
        binding.apply {
            etName.setText("")
            etEmail.setText("")
        }
    }

    // display list of student
    private fun initRecyclerView() {
        binding.rvStudents.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewAdapter{
                selectedItem: Student -> listItemClicked(selectedItem)
        }
        binding.rvStudents.adapter = adapter

        displayStudentList()
    }

    private fun displayStudentList(){
        viewModel.students.observe(this,{
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(student: Student){
//        Toast.makeText(
//            this,
//            "Student name is ${student.name}",
//            Toast.LENGTH_LONG
//        ).show()
        binding.apply {
            selectedStudent = student
            btnSave.text = "Update"
            btnClear.text = "Delete"
            isListItemClicked = true
            etName.setText(selectedStudent.name)
            etEmail.setText(selectedStudent.email)
        }
    }
}