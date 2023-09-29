package com.capgemini.starterkit.roomdatabase.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class EmployeeWithProject(
    @Embedded val projectEntity: ProjectEntity,
    @Relation(
        parentColumn = "projectId",
        entityColumn = "empProjectId"
    )
    val employees: List<EmployeeEntity>
)