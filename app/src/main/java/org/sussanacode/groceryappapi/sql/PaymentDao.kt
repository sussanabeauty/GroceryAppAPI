package org.sussanacode.groceryappapi.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import org.sussanacode.groceryappapi.model.Payment
import java.sql.SQLException

class PaymentDao (val context: Context) {

    val mydb: SQLiteDatabase

    init {
        mydb = OrderDBHelper(context).writableDatabase
    }


    fun addPayment(cardinfo: Payment): Boolean {
        try{
            val paymentValues: ContentValues = ContentValues()

            paymentValues.put("holder_name", cardinfo.nameoncard)
            paymentValues.put("card_number", cardinfo.cardNumnber)
            paymentValues.put("expiration_date", cardinfo.expirationDt)
            paymentValues.put("cvv_code", cardinfo.code)
            paymentValues.put("isPrimary", cardinfo.isPrimary)

            val cardID: Long = mydb.insert("payment", null, paymentValues)
            return cardID != -1L

        }catch(se: SQLiteException){
            se.printStackTrace()
            return false
        }
    }



    fun getPayment(): ArrayList<Payment>? {
        try {

            val paymentList = ArrayList<Payment>()
            val paymentCursor: Cursor = mydb.query("payment", null, null, null, null, null, null)

            while (paymentCursor.moveToNext()){

                val cardID = paymentCursor.getLong(0)
                val holdername = paymentCursor.getString(1)
                val cardNumber = paymentCursor.getLong(2)
                val expireDT = paymentCursor.getString(3)
                val cvvcode = paymentCursor.getString(4)
                val isPrimary = paymentCursor.getInt(5)

                val payment = Payment(cardID, holdername, cardNumber, expireDT, cvvcode, isPrimary)
                paymentList.add(payment)
            }
            return paymentList

        }catch(se: SQLException){
            se.printStackTrace()
            return null
        }
    }

    fun deletePayment(cardId: Long): Boolean{
        val rowsDeleted = mydb.delete("payment", "cardId=$cardId", null)
        return rowsDeleted == 1
    }

}