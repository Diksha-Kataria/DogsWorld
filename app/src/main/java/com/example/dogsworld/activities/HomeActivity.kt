package com.example.dogsworld.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.dogsworld.R
import com.example.dogsworld.data.Dog
import com.example.dogsworld.fragments.CartFragment
import com.example.dogsworld.fragments.HomeFragment
import com.example.dogsworld.localStorage.LocalPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var mBottomTabs: BottomNavigationView
    private lateinit var mTvToolbarTitle : TextView
    private lateinit var mIbLogout : ImageButton
    var dogList  :ArrayList<Dog>  = ArrayList()
    val pref = LocalPreferences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        populateList()
        initViews()
        navigateToHome()
    }

    private fun populateList() {
        val savedList = pref.getDogList()
        if(savedList != null){
            dogList.addAll(savedList)
        }
    }

    private fun initViews() {
        mBottomTabs = findViewById(R.id.bottomNavigationView)
        mTvToolbarTitle = findViewById(R.id.toolbar_title)
        mIbLogout = findViewById(R.id.ibLogout)
        setListeners()
    }

    private fun setListeners() {
        mBottomTabs.setOnItemSelectedListener {
            if(it.itemId.equals(R.id.home)){
                supportFragmentManager.popBackStack()
            }else if(it.itemId.equals(R.id.cart)){
                navigateToCart()
            }
            true
        }
        supportFragmentManager.addOnBackStackChangedListener(object : FragmentManager.OnBackStackChangedListener{
            override fun onBackStackChanged() {
                if(supportFragmentManager.backStackEntryCount ==0){
                    mTvToolbarTitle.text = getString(R.string.home)
                    val myFragment: HomeFragment? =
                        supportFragmentManager.findFragmentByTag(getString(R.string.home)) as HomeFragment?
                    myFragment?.refresh()
                }
            }
        })

        mIbLogout.setOnClickListener {
            showAlert()
        }
    }

    private fun showAlert() {
            val alertDialog = AlertDialog.Builder(this)

            alertDialog.apply {
                //setIcon(R.drawable.ic_hello)
                setTitle(context.getString(R.string.alert))
                setMessage("Are you sure you want to logout?")
                setPositiveButton(context.getString(R.string.ok)) { _: DialogInterface?, _: Int ->
                    pref.clearSavedData()
                    navigateToLogin()
                }
                setNegativeButton(context.getString(R.string.cancel)) { _, _ ->

                }
            }.create().show()
    }


    private fun navigateToCart() {
        mTvToolbarTitle.text = getString(R.string.cart)
        supportFragmentManager.beginTransaction()
            .add(R.id.flFragment, CartFragment(), getString(R.string.cart))
            .addToBackStack(getString(R.string.cart))
            .commitAllowingStateLoss()
    }

    private fun navigateToHome() {
        mTvToolbarTitle.text = getString(R.string.home)
        // handle back press
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, HomeFragment(),getString(R.string.home))
//            .addToBackStack(getString(R.string.home))
            .commitAllowingStateLoss()
    }

    private fun navigateToLogin() {
        val intent = Intent(this@HomeActivity,LoginActivity::class.java)
        startActivity(intent);
        this.finish()
        this.finishAffinity()
    }

    fun setListInStorage(){
        pref.setDogList(dogList)
    }
}