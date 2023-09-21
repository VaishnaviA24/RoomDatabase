package com.capgemini.starterkit.roomdatabase.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

private const val TableName = "Employee"

@Entity(tableName = TableName, indices = [Index(value = ["email", "name"], unique = true)])
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val empId: Int = 0,
    val name: String,
    val email: String,
    val empProjectId: String
)

//this is for one-to-one relation
data class EmployeeWithProject(
    @Embedded val employeeEntityInfo: EmployeeEntity,
    @Relation(
        parentColumn = "projectId",
        entityColumn = "empProjectId"
    )
    val employeeEntity: List<EmployeeEntity>
)

//this is for one-to-many relation
data class EmpWithMultipleProjects(
    @Embedded val projectEntity: ProjectEntity,
    @Relation(
        parentColumn = "projectId",
        entityColumn = "empProjectId"
    )
    val employeeEntity: List<EmployeeEntity>
)