package com.capgemini.starterkit.roomdatabase.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "user_info", indices = [Index(value = ["email", "name"], unique = true)])
data class MainInfoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id")
    val id: Int = 0,
    val name: String,
    val email: String,
    val userProjectId: String
)

@Entity(tableName = "project_details")
data class Project(
    @PrimaryKey val projectId: Long = 0,
    val projectName: String
)

//this is for one-to-one relation
data class UserWithProject(
    @Embedded val userInfo: MainInfoEntity,
    @Relation(
        parentColumn = "userProjectId",
        entityColumn = "projectId"
    )
    val project: Project
)

//this is for one-to-many relation
data class UserWithMultipleProjects(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "projectId",
        entityColumn = "userProjectId"
    )
    val user: List<MainInfoEntity>
)