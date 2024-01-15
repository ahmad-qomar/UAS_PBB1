package com.qomar.crudroomapp.room

data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val note: String,
    val penulis: String,
    val penerbit: String,
    val terbit: String
    ) {
    annotation class PrimaryKey(val autoGenerate: Boolean)
}

