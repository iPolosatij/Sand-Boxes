package com.digitallabstudio.sandboxes.data.room.dao

import androidx.room.*
import com.digitallabstudio.sandboxes.data.room.data.BdEntity

@Dao
interface BdDao {

    @Query("SELECT * FROM bd_table")
    fun getAll(): List<BdEntity>

    @Query("DELETE FROM bd_table")
    fun deleteAll()

    @Query("SELECT * FROM bd_table WHERE id LIKE :id LIMIT 1")
    fun findBdEntityById(id: String): BdEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg chat: BdEntity)

    @Delete
    fun delete(chat: BdEntity)
}