package com.example.exam2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.exam2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var users: MutableSet<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        users = mutableSetOf()

        binding.btnAddUser.setOnClickListener {
            createUser()
        }

        binding.btnRemove.setOnClickListener {
            deleteUser()
        }
    }


    private fun createUser() {

        var firstName = ""
        var secondName = ""
        var email = ""
        var age = 0

        if (binding.etFirstName.text.isNotEmpty()) {
            firstName = binding.etFirstName.text.toString()
            binding.etFirstName.setBackgroundResource(R.color.green)
        } else {
            binding.etFirstName.setBackgroundResource(R.color.red)
        }

        if (binding.etSecondName.text.isNotEmpty()) {
            secondName = binding.etSecondName.text.toString()
            binding.etSecondName.setBackgroundResource(R.color.green)
        } else {
            binding.etSecondName.setBackgroundResource(R.color.red)
        }

        if (binding.etEmail.text.trim().matches(emailPattern.toRegex())) {
            email = binding.etEmail.text.toString()
            binding.etEmail.setBackgroundResource(R.color.green)
        } else {
            binding.etEmail.setBackgroundResource(R.color.red)
        }

        if (binding.etAge.text.isNotEmpty()
            && binding.etAge.text.toString().toInt() > 0
            && binding.etAge.text.toString().toInt() < 150
        ) {
            age = binding.etAge.text.toString().toInt()
            binding.etAge.setBackgroundResource(R.color.green)
        } else {
            binding.etAge.setBackgroundResource(R.color.red)
        }

        if (firstName.isNotEmpty() && secondName.isNotEmpty() && email.isNotEmpty() && age > 0) {
            var user = User(firstName, secondName, email, age)
            if (users.contains(user)) {
                Toast.makeText(this, "User already exist", Toast.LENGTH_LONG).show()
            } else {
                users.add(user)
                Toast.makeText(this, "User added", Toast.LENGTH_LONG)
            }
        }
        showUsers()
    }

    private fun deleteUser() {
        var firstName = ""
        var secondName = ""
        var email = ""
        var age = 0

        if (binding.etFirstName.text.isNotEmpty()) {
            firstName = binding.etFirstName.text.toString()
            binding.etFirstName.setBackgroundResource(R.color.green)
        } else
            binding.etFirstName.setBackgroundResource(R.color.red)

        if (binding.etSecondName.text.isNotEmpty()) {
            secondName = binding.etSecondName.text.toString()
            binding.etSecondName.setBackgroundResource(R.color.green)
        } else
            binding.etSecondName.setBackgroundResource(R.color.red)

        if (binding.etEmail.text.matches(emailPattern.toRegex())) {
            email = binding.etEmail.text.toString()
            binding.etEmail.setBackgroundResource(R.color.green)
        } else
            binding.etEmail.setBackgroundResource(R.color.red)

        if (firstName.isNotEmpty() && secondName.isNotEmpty() && email.isNotEmpty() && age > 0) {
            var user = User(firstName, secondName, email, age)
            if (users.contains(user)) {
                users.remove(user)
                Toast.makeText(this, "User was deleted", Toast.LENGTH_LONG).show()
            } else
                Toast.makeText(this, "User not exist", Toast.LENGTH_LONG)
        }
        showUsers()
    }

    private fun showUsers() {
        binding.llUsers.removeAllViews()
        for (user in users) {
            var tvUser = TextView(this)
            var param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            tvUser.text = user.toString()
            tvUser.layoutParams = param
            binding.llUsers.addView(tvUser)
        }
    }
}