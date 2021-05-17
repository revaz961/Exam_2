package com.example.exam2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log.d
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.example.exam2.databinding.ActivityUpdateBinding
import com.google.android.material.snackbar.Snackbar

class UpdateActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var users: HashMap<Int, User>
    private var activeUserId: Int = -1
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.btnSave.setOnClickListener(this)
        users = intent.getSerializableExtra("users") as HashMap<Int, User>
        addUsers()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            in users.keys -> {
                if (activeUserId != v?.id)
                    modifyUser(v?.id as Int)
            }
            R.id.btnSave -> d("click", (v as TextView).text.toString())
        }
    }

    private fun modifyUser(id: Int){
        if(validateUser()) {
            users[activeUserId]!!.firstName = binding.etFirstName.text.trim().toString()
            users[activeUserId]!!.secondName = binding.etSecondName.text.trim().toString()
            users[activeUserId]!!.age = binding.etAge.text.trim().toString().toInt()
            users[activeUserId]!!.email = binding.etEmail.text.trim().toString()
            activeUserId = id
            setUser(activeUserId, users[activeUserId] as User)
        }
    }


    private fun addUsers() {
        users.forEach { setUserList(it.key, it.value) }
        activeUserId = users.keys.first()
        setUser(activeUserId, users[activeUserId] as User)
        binding.tvUser.text = "Users(${users.size})"
    }

    private fun setUser(id: Int, user: User) {
        activeUserId = id
        binding.etFirstName.setBackgroundResource(R.color.lightDark)
        binding.etSecondName.setBackgroundResource(R.color.lightDark)
        binding.etEmail.setBackgroundResource(R.color.lightDark)
        binding.etAge.setBackgroundResource(R.color.lightDark)
        binding.etFirstName.setText(user.firstName)
        binding.etSecondName.setText(user.secondName)
        binding.etAge.setText(user.age.toString())
        binding.etEmail.setText(user.email)
    }

    private fun setUserList(id: Int, user: User) {
        val tvUserItem = TextView(this)
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        tvUserItem.id = id
        tvUserItem.isClickable = true
        tvUserItem.setOnClickListener(this)
        tvUserItem.text = user.firstName
        tvUserItem.setTextColor(ContextCompat.getColor(this, R.color.textColor))
        tvUserItem.setBackgroundColor(ContextCompat.getColor(this, R.color.fieldColor))
        tvUserItem.textSize = 20.0f
        tvUserItem.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tvUserItem.setPadding(5)
        param.setMargins(5)
        tvUserItem.layoutParams = param
        binding.llUserList.addView(tvUserItem)
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

}