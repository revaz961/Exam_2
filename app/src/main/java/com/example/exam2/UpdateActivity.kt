package com.example.exam2

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        users = intent.getSerializableExtra("users") as HashMap<Int, User>
        addUsers()
    }

    private fun addUsers() {
        users.forEach {
            setUserList(it.key, it.value)
//            setUser(it.key, it.value)
        }

    }

    private fun setUser(id: Int, user: User) {
        setField("Firstname", user.firstName, id)
        setField("Secondname", user.secondName, id)
        setField("Age", user.age.toString(), id, InputType.TYPE_CLASS_NUMBER)
        setField(
            "Email", user.email, id,
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        )
    }

    private fun setUserList(id: Int, user: User) {
        val tvUserItem = TextView(this)
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        tvUserItem.id = id
        tvUserItem.isClickable = true
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

    private fun setField(
        field: String, text: String, id: Int,
        inputType: Int = InputType.TYPE_CLASS_TEXT
    ) {

    }

    override fun onClick(v: View?) {
        d("click",(v as TextView).text.toString())
        when(v?.id){
            in users.keys -> d("click",(v as TextView).text.toString())
            R.id.btnSave -> d("click",(v as TextView).text.toString())
            else -> d("click", "some click")
        }
    }
}