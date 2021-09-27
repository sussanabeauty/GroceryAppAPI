package org.sussanacode.groceryappapi.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class OrderDBHelper (val context: Context): SQLiteOpenHelper(context, "GroceryDB", null, 2){

    override fun onCreate(mydb: SQLiteDatabase?) {

        try {

            mydb?.execSQL(CREATE_TABLE_CART_QUERY)
            mydb?.execSQL(CREATE_TABLE_ADDRESS_QUERY)
            mydb?.execSQL(CREATE_TABLE_PAYMENT_QUERY)

        }catch(se: SQLiteException){
            se.printStackTrace();
            Toast.makeText(context, "Error while creating table : \n$se", Toast.LENGTH_LONG).show()
        }
    }

    override fun onUpgrade(mydb: SQLiteDatabase?, newVersion: Int, oldVersion: Int) {


    }


    companion object {
        const val CREATE_TABLE_CART_QUERY = """
            CREATE TABLE cart (
            cartID INTEGER PRIMARY KEY AUTOINCREMENT,
            productID TEXT,
            product_name TEXT, 
            quantity INTEGER,
            product_image TEXT,
            product_price DOUBLE
        )"""


        const val CREATE_TABLE_ADDRESS_QUERY = """
            CREATE TABLE shippingaddress (
            addressId INTEGER PRIMARY KEY AUTOINCREMENT,
            user_name TEXT, 
            user_phoneno INTEGER,
            email TEXT,
            houseNo INTEGER,
            address TEXT,
            city TEXT,
            state TEXT,
            zip TEXT,
            isPrimary INTEGER NOT NULL DEFAULT 0 CHECK(isPrimary IN (0, 1))
        )"""

        const val CREATE_TABLE_PAYMENT_QUERY = """
            CREATE TABLE payment (
            cardId INTEGER PRIMARY KEY AUTOINCREMENT,
            holder_name TEXT, 
            card_number INTEGER,
            expiration_date TEXT,
            cvv_code TEXT,
            isPrimary INTEGER NOT NULL DEFAULT 0 CHECK(isPrimary IN (0, 1))
        )"""



//        const val CREATE_TABLE_ORDER_QUERY = """
//            CREATE TABLE `order` (
//            orderID INTEGER PRIMARY KEY AUTOINCREMENT,
//            product_name TEXT,
//            product_price DOUBLE,
//            product_image TEXT,
//            order_total DOUBLE,
//            productID INTEGER,
//
//            FOREIGN KEY (productID)
//            REFERENCES Cart (productID)
//            ON UPDATE RESTRICT
//            ON DELETE RESTRICT)"""
    }
}