package space.dlsunity.arctour.di

import com.digitallabstudio.sandboxes.data.network.cache.ApplicationCacheStorage
import com.digitallabstudio.sandboxes.data.room.dao.BdDao
import com.digitallabstudio.sandboxes.data.room.db.AppDatabase
import com.digitallabstudio.sandboxes.data.room.repositories.BdDataRepository
import com.digitallabstudio.sandboxes.data.room.repositories.impl.BdDataRepositoryImpl
import org.koin.dsl.module
import space.dlsunity.arctour.data.network.cache.impl.ApplicationCacheStorageImpl

val dataModule = module {

    single<BdDataRepository> {
        BdDataRepositoryImpl(bdDao = get<BdDao>())
    }


    single<ApplicationCacheStorage> {
        ApplicationCacheStorageImpl()
    }

    // room data base
    single<AppDatabase> {
        AppDatabase.getInstance(get())
    }

    single<BdDao> { get<AppDatabase>().bdDao() }

}
