package org.sussanacode.groceryappapi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.sussanacode.groceryappapi.databinding.ActivityRegisterBinding
import org.sussanacode.groceryappapi.model.RegisterUser

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var requestQueue: RequestQueue

    val newUser: ArrayList<RegisterUser>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(baseContext)

        setUpEvent()
    }

    private fun setUpEvent() {
        binding.btnXout.setOnClickListener {
            startActivity(Intent(baseContext, LogInActivity::class.java))
        }

        binding.btnsignIn.setOnClickListener {  startActivity(Intent(baseContext, LogInActivity::class.java)) }

        binding.btnsignup.setOnClickListener {
            saveNewUser()
            startActivity(Intent(baseContext, LogInActivity::class.java))
        }


    }

    private fun saveNewUser() {
        val name = binding.etfirstname.text.toString()
        if(name.isEmpty()) {
            binding.etfirstname.error = "Please enter your first name"
            return
        }

        val mobile = binding.etmobile.text.toString()
        if(mobile.isEmpty()) {
            binding.etmobile.error = "Please enter your last name"
            return
        }

        val username = binding.etusername.text.toString()
        if(username.isEmpty()) {
            binding.etusername.error = "Please enter your username"
            return
        }

        val password = binding.etpassword.text.toString()
        if(password.isEmpty()) {
            binding.etpassword.error = "Please enter your password"
            return
        }

        val confirmedPassword = binding.etconfirmpassword.text.toString()
        if(confirmedPassword != password){
            binding.etconfirmpassword.error = "password does not match"
            return
        }

        val addnewUser = JSONObject()
        addnewUser.put("firstName", name)
        addnewUser.put("email", username)
        addnewUser.put("password", password)
        addnewUser.put("mobile", mobile)

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            REGISTER_URL,
            addnewUser,
            Response.Listener<JSONObject>{ response ->
                binding.pbuser.visibility = View.GONE

                if(response.getBoolean("error")){
                    Toast.makeText(baseContext, "User registration was not successful", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(baseContext, "User registered successfully", Toast.LENGTH_LONG).show()
                }

            }, Response.ErrorListener { error ->
                binding.pbuser.visibility = View.GONE
                error.printStackTrace()
                Toast.makeText(baseContext, "Error $error", Toast.LENGTH_LONG).show()
            }
        )

        requestQueue.add(jsonRequest)
        binding.pbuser.visibility = View.VISIBLE

    }


    companion object{
        const val REGISTER_URL = "https://grocery-second-app.herokuapp.com/api/auth/register"
    }
}