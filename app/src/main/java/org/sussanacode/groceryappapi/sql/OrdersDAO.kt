package org.sussanacode.groceryappapi.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import org.sussanacode.groceryappapi.model.Cart
import java.sql.SQLException

class OrdersDAO (val context: Context) {

//    val mydb: SQLiteDatabase
//
//    init {
//        mydb = OrderDBHelper(context).writableDatabase
//    }
//
//
//    fun addToCart(cart: Cart): Boolean {
//        try{
//            val cartvalues: ContentValues = ContentValues()
//
//            cartvalues.put("product_name", cart.productname)
//            cartvalues.put("quantity", cart.quantity)
//            cartvalues.put("product_price", cart.productprice)
//
//            val cartID: Long = mydb.insert("team", null, cartvalues)
//            return cartID != -1L
//
//        }catch(se: SQLiteException){
//            se.printStackTrace()
//            return false
//        }
//    }
//
////    fun getItemsInCart(): ArrayList<Cart>? {
////        try {
////
////            val cartList = ArrayList<Cart>()
////            val cartCursor: Cursor = mydb.query("cart", null, null, null, null, null, null)
////
////            while (cartCursor.moveToNext()){
////                val cartID = cartCursor.getLong(0)
////                val productname = cartCursor.getString(1)
////                val productqty = cartCursor.getInt(2)
////                val productprice = cartCursor.getDouble(3)
////
////                val cart = Cart(cartID, productname, productqty, productprice)
////                cartList.add(cart)
////            }
////            return cartList
////
////        }catch(se: SQLException){
////            se.printStackTrace()
////            return null
////        }
////    }
//
//
//    fun deleteItemInCart(cartID: Long): Boolean{
//        val rowsDeleted = mydb.delete("cart", "cartID=$cartID", null)
//        return rowsDeleted == 1
//    }
//
//
//    fun updateCart(cart: Cart): Boolean{
//        val cartvalues = ContentValues()
//
//        cartvalues.put("product_name", cart.productname)
//        cartvalues.put("quantity", cart.quantity)
//        cartvalues.put("product_price", cart.productprice)
//
//        val rowsUpdated = mydb.update("cart", cartvalues, "cartid=${cart.cartD}", null)
//
//        return rowsUpdated == 1
//    }
}