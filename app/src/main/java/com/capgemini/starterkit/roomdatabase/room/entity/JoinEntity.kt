package com.capgemini.starterkit.roomdatabase.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class EmployeeWithProject(
    @Embedded val employeeEntity: EmployeeEntity,
    @Relation(
        parentColumn = "empProjectId",
        entityColumn = "projectId"
    )
    val projectEntity: ProjectEntity
)

data class EmployeeWithMultipleProjects(
    @Embedded val employeeEntity: EmployeeEntity,
    @Relation(
        parentColumn = "empProjectId",
        entityColumn = "projectId"
    )
    val projects: List<ProjectEntity>
)