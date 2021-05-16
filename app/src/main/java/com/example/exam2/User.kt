package com.example.exam2

data class User(var firstName: String, var secondName: String, var email: String, var age: Int){
    companion object{
        var countId = 0
    }
    init{
        countId++
    }
    val id:Int = User.countId
}