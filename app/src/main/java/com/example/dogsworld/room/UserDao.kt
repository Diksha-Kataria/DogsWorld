package com.example.dogsworld.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dogsworld.data.Dog

@Dao
interface UserDao {
    @Query("SELECT * FROM dog")
    fun getAll(): List<Dog>

    @Query("SELECT * FROM dog WHERE cartCounter > 0")
    fun getCart(): List<Dog>

    @Insert
    fun insertAll(vararg dog: Dog)

    @Update
    fun updateCartValue()

}