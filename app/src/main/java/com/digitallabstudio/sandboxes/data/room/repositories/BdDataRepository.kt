package com.digitallabstudio.sandboxes.data.room.repositories

import com.digitallabstudio.sandboxes.data.room.data.Bd_data

interface BdDataRepository {

    suspend fun getBdEntityById(id: String): Bd_data

    suspend fun getAllBdEntities(): List<Bd_data>

    suspend fun deleteBdEntity(entity: Bd_data)

    suspend fun saveBdEntity(entity: Bd_data)

    suspend fun deleteAllBdEntities()

}