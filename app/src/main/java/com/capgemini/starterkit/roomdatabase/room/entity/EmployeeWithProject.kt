package com.capgemini.starterkit.roomdatabase.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class EmployeeWithProject(
    @Embedded val projectEntity: ProjectEntity, // Changed to ProjectEntity
    @Relation(
        parentColumn = "projectId", // Changed to "projectId"
        entityColumn = "empProjectId" // Changed to "empProjectId"
    )
    val employees: List<EmployeeEntity> // Changed to List<EmployeeEntity>
)

//data class EmployeeWithMultipleProjects(
//    @Embedded val employeeEntity: EmployeeEntity,
//    @Relation(
//        parentColumn = "empProjectId",
//        entityColumn = "projectId"
//    )
//    val projects: List<ProjectEntity>
//)