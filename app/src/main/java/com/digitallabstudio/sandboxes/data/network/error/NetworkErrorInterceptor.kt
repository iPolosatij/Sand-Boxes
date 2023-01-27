package space.dlsunity.arctour.data.network.error

import android.util.Log
import com.digitallabstudio.sandboxes.data.network.cache.ApplicationCacheStorage
import com.digitallabstudio.sandboxes.data.network.error.NetworkErrorHandler
import com.digitallabstudio.sandboxes.domain.model.exception.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import org.jivesoftware.smack.XMPPException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

internal class NetworkErrorInterceptor(
    private val errorHandler: NetworkErrorHandler,
    private val applicationCacheStorage: ApplicationCacheStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val response = chain.proceed(chain.request())
            if (!response.isSuccessful) {
                when (response.code) {
                    401 -> {
                        throw NetworkException.Unauthorized()
                    }
                    400 -> {
                        throw NetworkException.Unauthorized()
                    }
                    404 -> {
                        throw NetworkException.NotFound()
                    }
                    409 ->{
                        throw NetworkException.Conflict()
                    }
                    else -> {
                        Log.d("Unknown", response.code.toString())
                        throw NetworkException.Unknown()
                    }
                }
            }
            return response
        } catch (ex: ConnectException) {
            throw NetworkException.NoInternetAccess()
        } catch (ex: UnknownHostException) {
            throw NetworkException.NoInternetAccess()
        }catch (ex: XMPPException) {
            throw NetworkException.XMPPException(ex.toString())
        } catch (ex: TimeoutException) {
            throw NetworkException.TimeoutError()
        } catch (ex: SocketTimeoutException) {
            throw NetworkException.TimeoutError()
        } catch (ex: NetworkException.BlockDevice) {
            applicationCacheStorage.blockDevice.value = ex
            throw ex
        }catch (ex: UnknownHostException) {
            throw NetworkException.Unknown(ex.toString())
        }
    }
}