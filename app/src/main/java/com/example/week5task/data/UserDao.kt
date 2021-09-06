package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomapp.model.User
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)


    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table")
    fun getAll(): List<User>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table WHERE id IN (:id)")
    fun findById(id: Int): User

    @Insert
    fun addUser(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("Delete FROM user_table WHERE id IN (:id)")
    fun deleteUser(id: Int)

}