package org.sussanacode.groceryappapi.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.sussanacode.groceryappapi.databinding.ActivityLogInBinding
import org.sussanacode.groceryappapi.model.LogInUser

class LogInActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogInBinding
    var loginList: ArrayList<LogInUser>? = null
    lateinit var requestQueue: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(baseContext)
        setUpEvents()

    }


    private fun setUpEvents() {
        //change later
        binding.btnXout.setOnClickListener {
            startActivity(Intent(baseContext, MainActivity::class.java))
        }

        binding.btnsignin.setOnClickListener { getUserData() }


        binding.btnregisternew.setOnClickListener {  startActivity(Intent(baseContext, RegisterActivity::class.java)) }
    }

    private fun getUserData() {

        val username = binding.etusername.text.toString()
        if (username.isEmpty()) {
            binding.etusername.error = "Please enter your username or email"
            return
        }

        val password = binding.etpassword.text.toString()

        if (password.isEmpty()) {
            binding.etpassword.error = "Please enter your password"
            return
        }


        //make call to api
        val getUserdata = JSONObject()
        getUserdata.put("email", username)
        getUserdata.put("password", password)

        val jsonrequest = JsonObjectRequest(
            Request.Method.POST, LOGIN_URL, getUserdata,
            Response.Listener<JSONObject>{ response ->
                binding.pbuser.visibility = View.GONE

                if(response.has("token")){
                    val username = response.getJSONObject("user").getString("email")
                    val firstname = response.getJSONObject("user").getString("firstName")
                    val pass = response.getJSONObject("user").getString("password")
                    val userID = response.getJSONObject("user").getString("_id")

                    val sharedPreferences = getSharedPreferences("user_checked", MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("User_FIRSTNAME", firstname)
                    editor.putString("User_Key", username)
                    editor.putString("Pass_Key", pass)
                    editor.putString("USER_ID", userID)
                    editor.commit()
                    Toast.makeText(baseContext, "User successfully logged in ", Toast.LENGTH_LONG).show()
                    startActivity(Intent(baseContext, HomeScreenActivity::class.java))
                    finish()

                }else if (response.getBoolean("error") ){
                    Toast.makeText(baseContext, "${response.getBoolean(" error ")}", Toast.LENGTH_LONG).show()
                }

            }, Response.ErrorListener { error ->
                binding.pbuser.visibility = View.GONE
                error.printStackTrace()
                Toast.makeText(baseContext, "Error $error", Toast.LENGTH_LONG).show()
            }
        )

        jsonrequest.retryPolicy = DefaultRetryPolicy(10 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue.add(jsonrequest)
        binding.pbuser.visibility = View.VISIBLE

    }

    companion object{
        const val LOGIN_URL = "https://grocery-second-app.herokuapp.com/api/auth/login"
    }
}




//    private fun setUpEvents() {
//        //change later
//        binding.btnXout.setOnClickListener {
//            startActivity(Intent(baseContext, MainActivity::class.java))
//        }
//
//        binding.btnsignin.setOnClickListener {
//            getUserData()
//            startActivity(Intent(baseContext, MainActivity::class.java))
//
//        }
//
//
//        binding.btnregisternew.setOnClickListener {  startActivity(Intent(baseContext, RegisterActivity::class.java)) }
//    }
//
//    private fun getUserData() {
//
//        val username = binding.etusername.text.toString()
//        if (username.isEmpty()) {
//            binding.etusername.error = "Please enter your username or email"
//            return
//        }
//
//        val password = binding.etpassword.text.toString()
//
//        if (password.isEmpty()) {
//            binding.etpassword.error = "Please enter your password"
//            return
//        }
//
//
////        val loginUser = LogInUser(username, password)
////        loginList?.add(loginUser)
////
//
//
//        //make call to api
//        val getUserdata = JSONObject()
//        getUserdata.put("email", username)
//        getUserdata.put("password", password)
//
//        val jsonrequest = JsonObjectRequest(
//            Request.Method.POST, LOGIN_URL, getUserdata,
//            Response.Listener<JSONObject>{ response ->
//                binding.pbuser.visibility = View.GONE
//
//                if(response.has("token")){
//
//                    Toast.makeText(baseContext, "User  successfully", Toast.LENGTH_LONG).show()
//                    val sharedPreferences = getSharedPreferences("user_checked", MODE_PRIVATE)
//                    if (binding.cbcheckme.isChecked) {
//                        val checkme = binding.cbcheckme.isChecked
//
//                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                        editor.putString("KEY_EMAIL", username)
//                        editor.putString("KEY_PASSWORD", password)
//                        editor.putBoolean("KEY_CHECKED", checkme)
//                        editor.commit()
//                    } else {
//                        sharedPreferences.edit().clear().apply()
//                        Toast.makeText(baseContext, "user was not saved", Toast.LENGTH_LONG).show()
//                    }
//
//                }
//
//
//                if(response.getBoolean("error")){
//                    Toast.makeText(baseContext, "User registration was not successful", Toast.LENGTH_LONG).show()
//
//                }else{
//
//                }
//            }, Response.ErrorListener { error ->
//                binding.pbuser.visibility = View.GONE
//                error.printStackTrace()
//                Toast.makeText(baseContext, "Error $error", Toast.LENGTH_LONG).show()
//            }
//        )
//
//        jsonrequest.retryPolicy = DefaultRetryPolicy(10*1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//        requestQueue.add(jsonrequest)
//        binding.pbuser.visibility = View.VISIBLE
//
//    }
