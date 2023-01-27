package com.digitallabstudio.sandboxes.data.network.cache

import com.digitallabstudio.sandboxes.domain.model.exception.NetworkException
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

interface ApplicationCacheStorage {

    var lastCheckUpdate: LocalDateTime

    val blockDevice: MutableStateFlow<NetworkException.BlockDevice?>

}