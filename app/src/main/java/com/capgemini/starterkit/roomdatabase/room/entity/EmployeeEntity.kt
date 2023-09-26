package com.capgemini.starterkit.roomdatabase.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

private const val TableName = "Employee"

@Entity(tableName = TableName, indices = [Index(value = ["email", "name"], unique = true)])
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val empId: Int = 0,
    val name: String,
    val email: String,
    val empProjectId: String
)