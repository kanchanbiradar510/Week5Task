package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.UserDao
import com.example.roomapp.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }


    fun deleteUser(user:User){
        userDao.delete(user)
    }
    fun getAll(): List<User> {
        return userDao.getAll()
    }
    fun deleteUser(id:Int){
        userDao.deleteUser(id)
    }
    fun getUser(id: Int): User {
        return userDao.findById(id)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

}