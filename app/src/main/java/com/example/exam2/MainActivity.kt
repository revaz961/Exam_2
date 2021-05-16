package com.example.exam2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.*
import com.example.exam2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var users: MutableSet<User>
    private lateinit var checked: MutableList<View>
    private var activeUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        users = mutableSetOf(
            User("rezi", "giorgadze", "revaz961@gmail.com", 24),
            User("robi", "giorgadze", "revaz961@gmail.com", 18),
            User("lali", "giorgadze", "revaz961@gmail.com", 26),
            User("natia", "giorgadze", "revaz961@gmail.com", 28),
            User("giorgi", "wiklauri", "revaz961@gmail.com", 30),
            User("xatuna", "giorgadze", "revaz961@gmail.com", 50),
            User("gela", "giorgadze", "revaz961@gmail.com", 55)
        )

        showUsers()

        binding.btnAddUser.setOnClickListener {
            createUser()
        }

        binding.btnRemove.setOnClickListener {
            deleteUser()
        }

        binding.btnUpdate.setOnClickListener {
            checked = binding.llUsers.children.filter {
                it is CheckBox && it.isChecked
            }.toMutableList()

            if (checked.isNotEmpty()) {
                binding.btnAddUser.isClickable = false
                binding.btnRemove.isClickable = false
                binding.btnUpdate.text = "Save(${checked.size})"
                setUserForUpdate(checked[0])
                updateUser(checked[0])
            } else {
                binding.btnAddUser.isClickable = true
                binding.btnRemove.isClickable = true
                binding.btnUpdate.text = "Update"
                Toast.makeText(this, "Please check user", Toast.LENGTH_LONG).show()
            }

        }
    }


    private fun createUser() {
        if (validateUser()) {
            val user = User(
                binding.etFirstName.text.toString(),
                binding.etSecondName.text.toString(),
                binding.etEmail.text.toString(),
                binding.etAge.text.toString().toInt()
            )
            if (users.contains(user))
                Toast.makeText(this, "User already exist", Toast.LENGTH_LONG).show()
            else {
                users.add(user)
                addUser(user)
            }
        } else
            Toast.makeText(this, "User not valid", Toast.LENGTH_LONG).show()
    }

    private fun validateUser(): Boolean {
        var valid = true

        if (binding.etFirstName.text.isNotEmpty())
            binding.etFirstName.setBackgroundResource(R.color.green)
        else {
            binding.etFirstName.setBackgroundResource(R.color.red)
            valid = false
        }

        if (binding.etSecondName.text.isNotEmpty())
            binding.etSecondName.setBackgroundResource(R.color.green)
        else {
            binding.etSecondName.setBackgroundResource(R.color.red)
            valid = false
        }

        if (binding.etEmail.text.trim().matches(emailPattern.toRegex()))
            binding.etEmail.setBackgroundResource(R.color.green)
        else {
            binding.etEmail.setBackgroundResource(R.color.red)
            valid = false
        }

        if (binding.etAge.text.isNotEmpty()
            && binding.etAge.text.toString().toInt() > 0
            && binding.etAge.text.toString().toInt() < 150
        )
            binding.etAge.setBackgroundResource(R.color.green)
        else {
            binding.etAge.setBackgroundResource(R.color.red)
            valid = false
        }

        return valid
    }

    private fun addUser(user: User) {
        var checkBox = CheckBox(this)
        var param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val (firstName, secondName, email, age) = user
        checkBox.id = user.id
        checkBox.text = "$firstName $secondName $age years old $email"
        checkBox.setTextColor(ContextCompat.getColor(this, R.color.textColor))
        checkBox.setBackgroundColor(ContextCompat.getColor(this, R.color.fieldColor))
        checkBox.textSize = 15.0f
        checkBox.textAlignment = View.TEXT_ALIGNMENT_CENTER
        checkBox.setPadding(5)
        param.setMargins(5)
        checkBox.layoutParams = param
        binding.llUsers.addView(checkBox)
    }


    private fun deleteUser() {

        val deletedUsers = mutableListOf<CheckBox>()
        binding.llUsers.children.forEach {
            if (it is CheckBox && it.isChecked) {
                users.remove(
                    users.find { user ->
                        user.id == it.id
                    })
                binding.llUsers.removeView(it)
            }
        }
    }

    private fun updateUser(checkBox: View) {

    }

    private fun setUserForUpdate(checkBox: View) {
//        val user = users.find { it.id == checkBox.id } as User
//        binding.etFirstName.setText(user.firstName)
//        binding.etSecondName.setText(user.secondName)
//        binding.etEmail.setText(user.email)
//        binding.etAge.setText(user.age)
    }

    private fun showUsers() {
        for (user in users) {
            var checkBox = CheckBox(this)
            var param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val (firstName, secondName, email, age) = user
            checkBox.id = user.id
            checkBox.text = "$firstName $secondName $age years old $email"
            checkBox.setTextColor(ContextCompat.getColor(this, R.color.textColor))
            checkBox.setBackgroundColor(ContextCompat.getColor(this, R.color.fieldColor))
            checkBox.textSize = 15.0f
            checkBox.textAlignment = View.TEXT_ALIGNMENT_CENTER
            checkBox.setPadding(5)
            param.setMargins(5)
            checkBox.layoutParams = param
            binding.llUsers.addView(checkBox)
        }
    }
}