package com.example.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.roomdb.db.Student
import com.example.roomdb.db.StudentDB

class MainActivity : AppCompatActivity() {
    private lateinit var nameEditTest: EditText
    private lateinit var emailEditTest: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    private lateinit var viewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditTest = findViewById(R.id.etName)
        emailEditTest = findViewById(R.id.etEmail)
        saveButton = findViewById(R.id.btnSave)
        clearButton = findViewById(R.id.btnClear)

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
}