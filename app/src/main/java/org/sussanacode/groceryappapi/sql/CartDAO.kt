package org.sussanacode.groceryappapi.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import org.sussanacode.groceryappapi.model.Cart
import java.sql.SQLException

class CartDAO(val context: Context) {

    val mydb: SQLiteDatabase

    init {
        mydb = OrderDBHelper(context).writableDatabase
    }


    fun addToCart(cart: Cart): Boolean {
        try{
            val cartvalues: ContentValues = ContentValues()

            cartvalues.put("product_name", cart.productname)
            cartvalues.put("quantity", cart.quantity)
            cartvalues.put("product_image", cart.productImage)
            cartvalues.put("product_price", cart.productprice)

            val productID: Long = mydb.insert("cart", null, cartvalues)
            return productID != -1L

        }catch(se: SQLiteException){
            se.printStackTrace()
            return false
        }
    }

    fun getItemsInCart(): ArrayList<Cart>? {
        try {
            val cartList = ArrayList<Cart>()
            val cartCursor: Cursor = mydb.query("cart", null, null, null, null, null, null)

            while (cartCursor.moveToNext()){
                val productID = cartCursor.getLong(0)
                val productname = cartCursor.getString(1)
                val productqty = cartCursor.getInt(2)
                val productimg = cartCursor.getString(3)
                val productprice = cartCursor.getDouble(4)

                val cart = Cart(productID, productname, productqty, productimg, productprice)
                cartList.add(cart)
            }
            return cartList

        }catch(se: SQLException){
            se.printStackTrace()
            return null
        }
    }


    fun deleteItemInCart(productID: Long): Boolean{
        val rowsDeleted = mydb.delete("cart", "productID=$productID", null)
        return rowsDeleted == 1
    }


    fun updateCart(cart: Cart): Boolean{
        val cartvalues = ContentValues()

        cartvalues.put("product_name", cart.productname)
        cartvalues.put("quantity", cart.quantity)
        cartvalues.put("productImage", cart.productImage)
        cartvalues.put("product_price", cart.productprice)

        val rowsUpdated = mydb.update("cart", cartvalues, "productID=${cart.productID}", null)

        return rowsUpdated == 1
    }
}