package org.sussanacode.groceryappapi.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class OrderDBHelper (val context: Context): SQLiteOpenHelper(context, "CartItemDB", null, 1){

    override fun onCreate(mydb: SQLiteDatabase?) {

        try {

            mydb?.execSQL(CREATE_TABLE_CART_QUERY)
           // mydb?.execSQL(CREATE_TABLE_ORDER_QUERY)

        }catch(se: SQLiteException){
            se.printStackTrace();
            Toast.makeText(context, "Error while creating table : \n$se", Toast.LENGTH_LONG).show()
        }

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}


    companion object {
        const val CREATE_TABLE_CART_QUERY = """
            CREATE TABLE cart (
            productID INTEGER PRIMARY KEY AUTOINCREMENT,
            product_name TEXT, 
            quantity INTEGER,
            product_image TEXT,
            product_price DOUBLE)
        """

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