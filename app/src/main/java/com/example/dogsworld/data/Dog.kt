package com.example.dogsworld.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dog (@PrimaryKey var message: String, @ColumnInfo var status: String) {

   @ColumnInfo var cartCounter = 0

    constructor() : this(message = "", status = "") {

    }

}