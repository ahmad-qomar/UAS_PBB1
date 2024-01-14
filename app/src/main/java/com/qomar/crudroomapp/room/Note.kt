package com.qomar.crudroomapp.room

data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val note: String
    ) {
    annotation class PrimaryKey(val autoGenerate: Boolean)
}

