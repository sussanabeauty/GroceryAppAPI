package org.sussanacode.groceryappapi.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.sussanacode.groceryappapi.fragments.SubcategoryFragment

class TabsAdapter(val fragment: FragmentManager, val fragments: ArrayList<Fragment>):
    FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
    override fun getCount() = fragments.size

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }



//    override fun getPageTitle(position: Int): CharSequence? {
//        when(position){
//            0 -> return "Chicken Items"
//            1 -> return "Veggie Items"
//            2 -> return "Fruits & Veges"
//            3 -> return "Beef Items"
//        }
//        return super.getPageTitle(position)
//    }

}