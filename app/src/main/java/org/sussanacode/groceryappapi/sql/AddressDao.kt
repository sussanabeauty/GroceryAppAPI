package org.sussanacode.groceryappapi.sql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import org.sussanacode.groceryappapi.model.Cart
import org.sussanacode.groceryappapi.model.ShippingAddress
import java.sql.SQLException

class AddressDao(val context: Context) {

    val mydb: SQLiteDatabase

    init {
        mydb = OrderDBHelper(context).writableDatabase
    }


    fun addAddress(address: ShippingAddress): Boolean {
        try{
            val addressValues: ContentValues = ContentValues()

            addressValues.put("user_name", address.name)
            addressValues.put("user_phoneno", address.mobile)
            addressValues.put("email", address.email)
            addressValues.put("address", address.address)
            addressValues.put("city", address.city)
            addressValues.put("state", address.state)
            addressValues.put("zip", address.zip)

            val addressID: Long = mydb.insert("shippingaddress", null, addressValues)
            return addressID != -1L

        }catch(se: SQLiteException){
            se.printStackTrace()
            return false
        }
    }


    fun getAddress(): ArrayList<ShippingAddress>? {
        try {

            val addressList = ArrayList<ShippingAddress>()
            val addressCursor: Cursor = mydb.query("shippingaddress", null, null, null, null, null, null)

            while (addressCursor.moveToNext()){

                val addressID = addressCursor.getLong(0)
                val name = addressCursor.getString(1)
                val userphone = addressCursor.getString(2)
                val email = addressCursor.getString(3)
                val address = addressCursor.getString(4)
                val city = addressCursor.getString(5)
                val state = addressCursor.getString(6)
                val zip = addressCursor.getString(7)


                val addrs = ShippingAddress(addressID, name, userphone, email, address, city, state, zip)
                addressList.add(addrs)
            }
            return addressList

        }catch(se: SQLException){
            se.printStackTrace()
            return null
        }
    }


    fun deleteAddress(addressID: Long): Boolean{
        val rowsDeleted = mydb.delete("shippingaddress", "addressId=$addressID", null)
        return rowsDeleted == 1
    }


    fun updateAddress(address: ShippingAddress): Boolean {
        val addressValue = ContentValues()

        addressValue.put("user_name", address.name)
        addressValue.put("user_phoneno", address.mobile)
        addressValue.put("email", address.email)
        addressValue.put("address", address.address)
        addressValue.put("city", address.city)
        addressValue.put("state", address.state)
        addressValue.put("zip", address.zip)

        val rowsUpdated = mydb.update("shippingaddress", addressValue, "addressId=${address.addressID}", null)

        return rowsUpdated == 1

    }
}