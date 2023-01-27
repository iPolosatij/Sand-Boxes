package space.dlsunity.arctour.data.network.cache.impl

import com.digitallabstudio.sandboxes.data.network.cache.ApplicationCacheStorage
import com.digitallabstudio.sandboxes.domain.model.exception.NetworkException
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime
import java.time.ZoneId

class ApplicationCacheStorageImpl : ApplicationCacheStorage {
    override var lastCheckUpdate: LocalDateTime = LocalDateTime.now(ZoneId.systemDefault())

    override val blockDevice: MutableStateFlow<NetworkException.BlockDevice?> = MutableStateFlow(null)

}