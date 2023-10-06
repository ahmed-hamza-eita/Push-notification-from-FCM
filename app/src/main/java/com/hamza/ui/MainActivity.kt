package com.hamza.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.hamza.model.Data
import com.hamza.model.NotificationModel
import com.hamza.pushnotificationfromfcm.R
import com.hamza.pushnotificationfromfcm.databinding.ActivityMainBinding
import com.hamza.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val vm: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        actions()
        observer()
    }

    private fun observer() {
        lifecycleScope.launchWhenStarted {
            vm.response.collect {
                when (it?.status) {
                    NetworkState.Status.RUNNING -> {
                        Toast.makeText(this@MainActivity, "RUNNING", Toast.LENGTH_SHORT).show()
                    }

                    NetworkState.Status.SUCCESS -> {
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()

                    }

                    NetworkState.Status.FAILED -> {
                        Toast.makeText(this@MainActivity, it.msg.toString(), Toast.LENGTH_SHORT).show()

                    }

                    else -> Unit
                }
            }
        }
    }

    private fun actions() {
        binding.btnPush.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                val token = task.result
                vm.sendNotification(
                    NotificationModel(token, Data("Test Notification", "Hello World!!"))
                )

            })

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}