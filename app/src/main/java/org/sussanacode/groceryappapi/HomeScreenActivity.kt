package org.sussanacode.groceryappapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import org.sussanacode.groceryappapi.adapters.TabsAdapter
import org.sussanacode.groceryappapi.databinding.ActivityHomeScreenBinding
import org.sussanacode.groceryappapi.fragments.*
import org.sussanacode.groceryappapi.model.Category
import org.sussanacode.groceryappapi.model.Product
import org.sussanacode.groceryappapi.model.Subcategory

class HomeScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeScreenBinding
   // lateinit var tabsAdapter: TabsAdapter
    lateinit var categoryFragment: CategoryFragment
    lateinit var subcatFragment: SubcategoryFragment
    lateinit var cartFragment: CartViewFragment
    lateinit var productFragment: ProductFragment
    lateinit var currentFragment: Fragment


    lateinit var navtoggle: ActionBarDrawerToggle
    val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //add fragments
        categoryFragment = CategoryFragment()
        subcatFragment = SubcategoryFragment()
        productFragment = ProductFragment()
        cartFragment = CartViewFragment()


        //send category id to subcategory fragment
        categoryFragment.setOnClickCategory {
            subcatFragment.getCategoryID(it)
            loadSubCatFragment(it)

        }

//        //send subcategoryID to product fragment
//        subcatFragment.setOnClickSubcategory {
//            productFragment.getSubcategoryID(it)
//            loadProductFragment(it)
//        }

        //send subcategoryID to product fragment
        subcatFragment.setOnClickSubcategory {
            productFragment.getSubcategoryID(it)
            loadProductFragment(it)
        }


        //send productname to cart fragment
        productFragment.setOnClickAddProduct{
            cartFragment.getproductByName(it)
           loadCartView(it)
        }


        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, categoryFragment)
            .addToBackStack("CategoriesFragment").commit()


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        //Action bar toggle
        navtoggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open , R.string.nav_close)
        binding.drawerLayout.addDrawerListener(navtoggle)
        navtoggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navMenu.setNavigationItemSelectedListener {
            when (it.itemId){

                R.id.action_user_profile->{
                    currentFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, currentFragment).commit()
                }

                R.id.action_orders ->{
                    currentFragment = OrdersFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, currentFragment).commit()

                }

                R.id.action_order_tracking->{
                    currentFragment = OrderTrackingFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, currentFragment).commit()
                }

                R.id.action_help->{
                    currentFragment = HelpFAQFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, currentFragment).commit()
                }

                R.id.action_sing_out->{
                    currentFragment = SignOutFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, currentFragment).commit()
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }

    private fun loadCartView(product: Product) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, cartFragment)
            .addToBackStack("ProductFragment").commit()
    }



    private fun loadProductFragment(subcategory: Subcategory) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, productFragment)
            .addToBackStack("ProductFragment").commit()

    }

    private fun loadSubCatFragment(category: Category) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, subcatFragment)
            .addToBackStack("SubcategoryFragment")
            .commit()
    }

    /** Navigation implementation */
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        if(navtoggle.onOptionsItemSelected(item)){
            return true
        }

        if(item.itemId == R.id.action_cart){

//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragment_container, cartFragment)
//            .addToBackStack("ProductFragment")
//            .commit()
        return true

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val itemdata = menu.findItem(R.id.action_cart)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_to_cart, menu)
        return super.onCreateOptionsMenu(menu)

    }


}