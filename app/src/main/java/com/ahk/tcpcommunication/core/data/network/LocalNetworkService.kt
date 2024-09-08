package com.ahk.tcpcommunication.core.data.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.ahk.tcpcommunication.R
import com.ahk.tcpcommunication.core.model.DataException
import com.ahk.tcpcommunication.base.model.ErrorCode
import com.ahk.tcpcommunication.base.model.ErrorModel
import io.reactivex.rxjava3.core.Single
import java.net.Inet4Address
import javax.inject.Inject

class LocalNetworkService @Inject constructor(
    private val connectivityManager: ConnectivityManager?,
) : NetworkService {
    override fun fetchIp(): Single<String> {
        return connectivityManager?.let { connectivityManager ->
            val activeNetwork = connectivityManager.activeNetwork ?: return Single.error(
                DataException.ConnectionError(
                    ErrorModel(
                        ErrorCode.CONNECTION_ERROR,
                        R.string.Connection_Error,
                        R.string.There_Is_No_Active_Network_Connection,
                        R.drawable.no_wifi_icon,
                    ),
                ),
            )

            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return Single.error(
                    DataException.ConnectionError(
                        ErrorModel(
                            ErrorCode.CONNECTION_ERROR,
                            R.string.Connection_Error,
                            R.string.There_Is_No_Wifi_Connection,
                            R.drawable.no_wifi_icon,
                        ),
                    ),
                )

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                val linkProperties =
                    connectivityManager.getLinkProperties(activeNetwork) ?: return Single.error(
                        DataException.ConnectionError(
                            ErrorModel(
                                ErrorCode.CONNECTION_ERROR,
                                R.string.Connection_Error,
                                R.string.There_Is_No_Wifi_Connection,
                                R.drawable.no_wifi_icon,
                            ),
                        ),
                    )
                val addresses = linkProperties.linkAddresses
                addresses.forEach {
                    if (it.address is Inet4Address && !it.address.isLoopbackAddress) {
                        it.address.hostAddress?.let { ip ->
                            return Single.just(ip)
                        }
                    }
                }
            }
            Single.error(
                DataException.ConnectionError(
                    ErrorModel(
                        ErrorCode.CONNECTION_ERROR,
                        R.string.Connection_Error,
                        R.string.There_Is_No_Wifi_Connection,
                        R.drawable.no_wifi_icon,
                    ),
                ),
            )
        } ?: Single.error(
            DataException.ConnectionError(
                ErrorModel(
                    ErrorCode.CONNECTION_ERROR,
                    R.string.Connection_Error,
                    R.string.There_Is_No_Wifi_Connection,
                    R.drawable.no_wifi_icon,
                ),
            ),
        )
    }

    override fun isWifiConnected(): Single<Boolean> {
        return connectivityManager?.let { connectivityManager ->
            val activeNetwork = connectivityManager.activeNetwork ?: return Single.just(false)

            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return Single.just(
                    false,
                )

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return Single.just(true)
            }
            Single.just(false)
        } ?: Single.just(false)
    }
}
