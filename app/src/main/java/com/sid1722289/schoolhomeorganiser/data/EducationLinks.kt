package com.sid1722289.schoolhomeorganiser.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL
@Entity(tableName = "educational_links_table")
data class EducationLinks(
    @PrimaryKey(autoGenerate = true)
    var linkId: Long = 0L,

    @ColumnInfo(name = "link_title")
    val title: String = "",

    @ColumnInfo(name = "link_url")
    var url: String = "",

    @ColumnInfo(name = "link_description")
    var description: String = ""
)
