package com.digitallabstudio.sandboxes.data.room.data

import androidx.room.Entity
import androidx.room.TypeConverters
import com.digitallabstudio.sandboxes.data.room.data.BdEntity.Companion.TABLE_NAME
import com.digitallabstudio.sandboxes.data.room.utils.Converters

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
@TypeConverters(Converters::class)
class BdEntity(
    var id: String,
    var name: String = "",
    var lastName: String = "",
    var tel: String = "",
    var avatar: String = ""
) {
    companion object {
        const val TABLE_NAME = "bd_table"
    }
}

fun Bd_data.toEntity(): BdEntity {
    return  BdEntity(
        id = id,
        name = name,
        lastName = lastName,
        tel = tel,
        avatar = avatar
    )
}

fun BdEntity.toData(): Bd_data {
    return  Bd_data(
        id = id,
        name = name,
        lastName = lastName,
        tel = tel,
        avatar = avatar
    )
}