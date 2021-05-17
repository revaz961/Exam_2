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
    private lateinit var users: HashMap<Int, User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val rezi = User("rezi", "giorgadze", "revaz961@gmail.com", 24)
        val robi = User("robi", "giorgadze", "robi961@gmail.com", 18)
        val laluka = User("laliuka", "giorgadze", "laluka@gmail.com", 26)
        val natia = User("natia", "giorgadze", "natia@gmail.com", 28)
        val giorgi = User("giorgi", "wiklauri", "revaz961@gmail.com", 30)
        val xatuna = User("xatuna", "giorgadze", "revaz961@gmail.com", 50)
        val gela = User("gela", "giorgadze", "revaz961@gmail.com", 55)
        users = hashMapOf(
            rezi.id to rezi,
            robi.id to robi,
            laluka.id to laluka,
            natia.id to natia,
            giorgi.id to giorgi,
            xatuna.id to xatuna,
            gela.id to gela
        )

        showUsers()

        binding.btnAddUser.setOnClickListener {
            createUser()
        }

        binding.btnRemove.setOnClickListener {
            deleteUser()
        }

        binding.btnUpdate.setOnClickListener {
            updateUser()
        }
    }

    private fun createUser() {
        if (!validateUser()) {
            Toast.makeText(this, "User not valid", Toast.LENGTH_LONG).show()
            return
        }
        val user = User(
            binding.etFirstName.text.trim().toString(),
            binding.etSecondName.text.trim().toString(),
            binding.etEmail.text.trim().toString(),
            binding.etAge.text.trim().toString().toInt()
        )
        if (users.values.contains(user))
            Toast.makeText(this, "User already exist", Toast.LENGTH_LONG).show()
        else {
            users[user.id] = user
            addUser(user)
            binding.users.text = "Users(${users.size}):"
        }
    }

    private fun validateUser(): Boolean {
        var valid = true
        binding.etFirstName.setBackgroundResource(R.color.green)
        binding.etSecondName.setBackgroundResource(R.color.green)
        binding.etEmail.setBackgroundResource(R.color.green)
        binding.etAge.setBackgroundResource(R.color.green)

        if (binding.etFirstName.text.trim().isEmpty()) {
            binding.etFirstName.setBackgroundResource(R.color.red)
            valid = false
        }
        if (binding.etSecondName.text.trim().isEmpty()) {
            binding.etSecondName.setBackgroundResource(R.color.red)
            valid = false
        }
        if (!binding.etEmail.text.trim().matches(emailPattern.toRegex())) {
            binding.etEmail.setBackgroundResource(R.color.red)
            valid = false
        }
        if (binding.etAge.text.trim().isEmpty()
            || binding.etAge.text.trim().toString().toInt() < 0
            || binding.etAge.text.trim().toString().toInt() > 150
        ) {
            binding.etAge.setBackgroundResource(R.color.red)
            valid = false
        }
        return valid
    }

    private fun addUser(user: User) {
        val checkBox = CheckBox(this)
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val (firstName, secondName, email, age) = user
        checkBox.id = user.id
        checkBox.text = "$firstName $secondName $age years old $email"
        checkBox.setTextColor(ContextCompat.getColor(this, R.color.textColor))
        checkBox.setBackgroundColor(ContextCompat.getColor(this, R.color.fieldColor))
        checkBox.textSize = 20.0f
        checkBox.textAlignment = View.TEXT_ALIGNMENT_CENTER
        checkBox.setPadding(5)
        param.setMargins(5)
        checkBox.layoutParams = param
        binding.llUsers.addView(checkBox)
    }


    private fun deleteUser() {
        binding.llUsers.children.forEach {
            if (it is CheckBox && it.isChecked)
                users.remove(it.id)
        }
        showUsers()
    }

    private fun updateUser() {
        val checkedUser =
            binding.llUsers.children.filter { it is CheckBox && it.isChecked }.map { it.id }
        val checkedUserMap = users.filter{it.key in checkedUser} as HashMap<Int,User>
        if (checkedUserMap.isNotEmpty()) {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("users", checkedUserMap)
            startActivity(intent)
        }
    }

    private fun showUsers() {
        binding.llUsers.removeAllViews()
        users.forEach {
            addUser(it.value)
        }
        binding.users.text = "Users(${users.size})"
    }
}