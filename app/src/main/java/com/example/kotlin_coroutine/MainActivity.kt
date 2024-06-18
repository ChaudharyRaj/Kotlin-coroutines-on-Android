package com.example.kotlin_coroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.kotlin_coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {
    val TAG = "COROUTINE"
    lateinit var binding : ActivityMainBinding
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { incrimentCount() }

        binding.button2.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                printFollowers2()
            }
        }
    }

    //Coroutine function with async
    suspend fun printFollowers2(){
        CoroutineScope(Dispatchers.IO).launch {
            var fb = async{ getFbFollowers() }
            var insta = async { getInstaFollowers() }
            Log.d(TAG,"Insta : ${fb.await()} AND ${insta.await()}")
        }
    }

    // Coroutine function with launch
    suspend fun printFollowers(){

        var FbFollowers = 0;
        var instFollowers = 0;

        val job = CoroutineScope(Dispatchers.IO).launch {
            FbFollowers =  getFbFollowers()
        }

        val job2 = CoroutineScope(Dispatchers.IO).launch{
            instFollowers = getInstaFollowers()
        }
        job.join()
        job2.join()
        Log.d(TAG,"Insta : $instFollowers AND $FbFollowers")
    }

    suspend fun getInstaFollowers():Int{
        Log.d(TAG,"$coroutineContext")
        delay(1000)
        return 300
    }
    suspend fun getFbFollowers(): Int {
        Log.d(TAG,"${coroutineContext}")
        delay(1000)
        return 101;
    }

    private fun incrimentCount() {
        binding.textView.text = counter++.toString()
    }
}

