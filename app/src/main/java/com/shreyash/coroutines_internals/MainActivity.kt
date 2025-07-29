package com.shreyash.coroutines_internals

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.shreyash.coroutines_internals.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    lateinit var binding: ActivityMainBinding
    private var numThread40 = AtomicInteger(0)
    private var numThread4 = AtomicInteger(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onLaunchUsing40Threads(view: View) {
        launch40threads()
    }

    fun onLaunchUsing4Threads(view: View) {
        launch4Threads()
    }

    fun launchThreads(view: View) {
        launchThreads()
    }

    fun launchCoroutines(view: View) {
        launchCoroutines()
    }


    private fun launch40threads() {
        numThread40 = AtomicInteger(0)
        val threadPool = Executors.newFixedThreadPool(40)
        val startTime = System.currentTimeMillis()
        repeat(10_000) {
            threadPool.execute {
                longRunningTask40(startTime)
            }
        }
    }

    private fun launch4Threads() {
        numThread4 = AtomicInteger(0)
        val threadPool = Executors.newFixedThreadPool(4)
        val startTime = System.currentTimeMillis()
        repeat(4) {
            threadPool.execute {
                repeat(2500) {
                    longRunningTask4(startTime)
                }
            }
        }
    }

    private fun longRunningTask40(startTime: Long) {
        if (numThread40.incrementAndGet() == 10_000) {
            Log.d(TAG, "Result using 40 thread : = ${System.currentTimeMillis() - startTime}")
            runOnUiThread {
                binding.tvResult40.text =
                    "Result:  ${System.currentTimeMillis() - startTime}ms"
            }
        }
    }

    private fun longRunningTask4(startTime: Long) {
        if (numThread4.incrementAndGet() == 10_000) {
            Log.d(TAG, "Result using 4 thread : = ${System.currentTimeMillis() - startTime}")
            runOnUiThread {
                binding.tvResult4.text =
                    "Result:  ${System.currentTimeMillis() - startTime}ms"
            }

        }
    }

    private fun launchThreads() {
        val totalThreads = 100_000
        val counter = AtomicInteger(0)
        binding.tvResultThreads.text = "Status: Launched"
        repeat(totalThreads) {
            Thread {
                Thread.sleep(5000L)
                if (counter.incrementAndGet() == totalThreads) {
                    runOnUiThread {
                        binding.tvResultThreads.text = "Status: Completed"
                    }
                }
            }.start()

        }
    }


    private fun launchCoroutines() {
        val totalCoroutines = 100_000
        val counter = AtomicInteger(0)

        binding.tvResultCoroutines.text = "Status: Launched"

        repeat(totalCoroutines) {
            GlobalScope.launch {
                delay(5000L)
                if (counter.incrementAndGet() == totalCoroutines) {
                    runOnUiThread {
                        binding.tvResultCoroutines.text = "Status: Completed"
                    }
                }
            }
        }
    }


}