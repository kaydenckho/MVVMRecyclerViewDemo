package com.example.myapplication.ui.general.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class Data(
    @PrimaryKey val programId:Int,
    val title:String?,
    val description: String?,
    val imageUrl: String?,
    val programUrl:String?,
    val displayOrder: Int?,
    val showInList: Boolean?,
    val mustLogin: Boolean?,
    val needLocation: Boolean?,
    val shareTitle: String?,
    val shareUrl: String?,
    val shareRemark: String?
    )
