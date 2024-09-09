package com.ahk.tcpcommunication.core.data.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.ahk.tcpcommunication.R
import com.ahk.tcpcommunication.base.model.ErrorCode
import com.ahk.tcpcommunication.base.model.ErrorModel
import com.ahk.tcpcommunication.core.model.DataException
import io.reactivex.rxjava3.core.Completable
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

    override fun isWifiConnected(): Completable {
        val noWifiException = DataException.ConnectionError(
            ErrorModel(
                ErrorCode.CONNECTION_ERROR,
                R.string.Connection_Error,
                R.string.There_Is_No_Wifi_Connection,
                R.drawable.no_wifi_icon,
            ),
        )

        val noActiveNetworkException = DataException.ConnectionError(
            ErrorModel(
                ErrorCode.CONNECTION_ERROR,
                R.string.Connection_Error,
                R.string.There_Is_No_Active_Network_Connection,
                R.drawable.no_wifi_icon,
            ),
        )
        return connectivityManager?.let { connectivityManager ->
            val activeNetwork = connectivityManager.activeNetwork ?: return Completable.error(
                noActiveNetworkException,
            )

            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                ?: return Completable.error(
                    noWifiException,
                )

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return Completable.complete()
            }
            Completable.error(
                noWifiException,
            )
        } ?: Completable.error(
            noActiveNetworkException,
        )
    }
}
