package com.digitallabstudio.sandboxes.data.room.repositories.impl

import com.digitallabstudio.sandboxes.data.room.dao.BdDao
import com.digitallabstudio.sandboxes.data.room.data.Bd_data
import com.digitallabstudio.sandboxes.data.room.data.toData
import com.digitallabstudio.sandboxes.data.room.data.toEntity
import com.digitallabstudio.sandboxes.data.room.repositories.BdDataRepository

class BdDataRepositoryImpl(
    private val bdDao: BdDao
): BdDataRepository {
    override suspend fun getBdEntityById(id: String): Bd_data {
        return bdDao.findBdEntityById(id).toData()
    }

    override suspend fun getAllBdEntities(): List<Bd_data> {
        val outList = ArrayList<Bd_data>()
        val savedChats = bdDao.getAll()
        for (chatsEntity in savedChats){
            outList.add(chatsEntity.toData())
        }
        return outList
    }

    override suspend fun deleteBdEntity(data: Bd_data) {
        bdDao.delete(data.toEntity())
    }

    override suspend fun saveBdEntity(data: Bd_data) {
        bdDao.insertAll(data.toEntity())
    }

    override suspend fun deleteAllBdEntities() {
        bdDao.deleteAll()
    }
}