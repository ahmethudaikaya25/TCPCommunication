package com.ahk.tcpcommunication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.ahk.tcpcommunication.databinding.ActivityTcpCommunicationBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TcpCommunicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTcpCommunicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityTcpCommunicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
