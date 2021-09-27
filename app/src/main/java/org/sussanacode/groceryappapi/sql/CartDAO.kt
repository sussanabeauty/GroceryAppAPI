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
    var productSet: HashMap<String, Int> = HashMap()

    init {
        mydb = OrderDBHelper(context).writableDatabase
        val prodCursor: Cursor = mydb.query("cart",
            arrayOf("productID", "quantity"), null, null, null, null, null)

        while (prodCursor.moveToNext()){
            val productID = prodCursor.getString(0)
            val qty = prodCursor.getInt(1)

            if (productSet.containsKey(productID))
            {
                productSet.put(productID, productSet.get(productID)!!.plus(qty))
            }
            else
            {
                productSet.put(productID, qty)
            }

        }
        print(productSet)
    }

    fun oldQty(prodId:String): Int
    {
        if (productSet.containsKey(prodId))
        {
            return productSet.get(prodId)!!
        }
        else
        {
            return 0
        }
    }

    fun addToCart(cart: Cart): Boolean {


        try{
            if (productSet.containsKey(cart.productID))
            {
                val newQty = productSet.get(cart.productID)!!.plus(cart.quantity)
                productSet.put(cart.productID, newQty)
                val cartvalues = ContentValues()
                cartvalues.put("quantity",newQty )

                mydb.update("cart", cartvalues, "productID= \"${cart.productID}\"", null)
                return true
            }
            else {
                productSet.put(cart.productID, cart.quantity)

                val cartvalues: ContentValues = ContentValues()

                cartvalues.put("productID", cart.productID)
                cartvalues.put("product_name", cart.productname)
                cartvalues.put("quantity", cart.quantity)
                cartvalues.put("product_image", cart.productImage)
                cartvalues.put("product_price", cart.productprice)
//            cartvalues.put("product_items", cart.itemnumber)
//            cartvalues.put("product_subtotal", cart.subtotal)

                val cartID: Long = mydb.insert("cart", null, cartvalues)
                return cartID != -1L
            }


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
                val cartID = cartCursor.getLong(0)
                val productID = cartCursor.getString(1)
                val productname = cartCursor.getString(2)
                val productqty = cartCursor.getInt(3)
                val productimg = cartCursor.getString(4)
                val productprice = cartCursor.getDouble(5)
//                val productitems = cartCursor.getInt(6)
//                val productsubtotal = cartCursor.getDouble(7)

                val cart = Cart(cartID, productID, productname, productqty, productimg, productprice)
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

        // cartvalues.put("productID", cart.productID)
        cartvalues.put("product_name", cart.productname)
        cartvalues.put("quantity", cart.quantity)
        cartvalues.put("productImage", cart.productImage)
        cartvalues.put("product_price", cart.productprice)

        val rowsUpdated = mydb.update("cart", cartvalues, "productID=${cart.productID}", null)
        return rowsUpdated == 1
    }
}