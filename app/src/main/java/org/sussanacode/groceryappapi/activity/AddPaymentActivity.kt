package org.sussanacode.groceryappapi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.ActivityAddPaymentBinding
import org.sussanacode.groceryappapi.model.Payment
import org.sussanacode.groceryappapi.sql.AddressDao
import org.sussanacode.groceryappapi.sql.PaymentDao

class AddPaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddPaymentBinding
    lateinit var paymentDao: PaymentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        paymentDao = PaymentDao(baseContext)

        binding.btnback.setOnClickListener {

            startActivity(Intent(baseContext, HomeScreenActivity::class.java))
        }


        binding.btnAddcard.setOnClickListener {
            val holdername = binding.etnameholder.text.toString()
            val cardNo = binding.etcardNumber.text.toString().toLong()
            val expireDT = binding.etexpireDT.text.toString()
            val cvvcode = binding.etcvvcode.text.toString()
            val isPrimary = if(binding.isPrimary.isChecked){ 1 }else { 0 }


            val payment = Payment(0, holdername, cardNo, expireDT, cvvcode, isPrimary)

            val savepayment = paymentDao.addPayment(payment)

            if(savepayment){
                Toast.makeText(baseContext, "Payment added successfully", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(baseContext, "Error whiles adding Player", Toast.LENGTH_LONG).show()
            }
        }


    }
}