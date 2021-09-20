package org.sussanacode.groceryappapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import org.sussanacode.groceryappapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val LAUNCH_LOGIN_SCREEN = 200
    val LAUNCH_HOME_SCREEN = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getwaitingTime()
    }

    private fun getwaitingTime() {
        val screentimeout = 3000L
        val sharedPref = getSharedPreferences("user_checked", MODE_PRIVATE)
        val username = sharedPref.getString("User_Key", "").toString();
        val password = sharedPref.getString("Pass_Key", "").toString();


        if((username != null) && (password != null)){
            handler.sendEmptyMessageDelayed(LAUNCH_HOME_SCREEN, 2000)
        }else {
            handler.sendEmptyMessageDelayed(LAUNCH_LOGIN_SCREEN, 2000)
        }
//        if(sharedPref.contains("User_Key") && sharedPref.contains("Pass_Key")){
//            Log.d("username", "$username")
//            Log.d("password", "$password")
//            handler.sendEmptyMessageDelayed(LAUNCH_HOME_SCREEN, screentimeout)
//        }else {
//
//            handler.sendEmptyMessageDelayed(LAUNCH_LOGIN_SCREEN, screentimeout)
//        }
    }

     val handler = object : Handler(){
        override fun handleMessage(msg: Message){
            super.handleMessage(msg)

            if(msg.what == LAUNCH_LOGIN_SCREEN){
                startActivity(Intent(baseContext, LogInActivity::class.java))
                finish()
            } else if (msg.what == LAUNCH_HOME_SCREEN){
                startActivity(Intent(baseContext, HomeScreenActivity::class.java))
                finish()
            }
        }
    }


}