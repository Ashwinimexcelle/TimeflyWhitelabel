package com.mexcelle.whitelable.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.util.Utility
import kotlinx.android.synthetic.main.custome_toolbar.*
import kotlinx.android.synthetic.main.nav_header_main.*
import android.view.View
import com.mexcelle.whitelable.util.Constants
import android.content.Intent
import com.mexcelle.whitelable.ui.login.LoginActivity


class MainActivity : AppCompatActivity() ,NavigationView.OnNavigationItemSelectedListener{

    private var headeImage: ImageView? = null
    private lateinit var headeName: TextView
    private lateinit var headerEmailId: TextView
    public lateinit var navController: NavController
    private  lateinit var appBarConfiguration: AppBarConfiguration
    private  lateinit var toolbar: Toolbar
    private  lateinit var navigationView: NavigationView
    private  lateinit var drawerLayout: DrawerLayout
    private  lateinit var navImage: TextView

    companion object{

        lateinit var screenName:TextView
        lateinit var bottomNavigationView: BottomNavigationView

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        screenName = findViewById(R.id.screen_name)
        navImage = toolbar!!.findViewById(R.id.nav_btn)



        Utility.setSolidFontAwesome(this,navImage)
        //Utility.setRegular(this,screenName)

        bottomNavigationView = findViewById(R.id.bottum_id)
        navigationView=findViewById(R.id.navigation_id)
        drawerLayout=findViewById(R.id.drawer_layout)
        val header: View = navigationView.getHeaderView(0)
        headeName = header.findViewById(R.id.user_name)
        headerEmailId = header.findViewById(R.id.user_email_id)

        headeName.text = "Hello, "+Constants.USER_NAME
        headerEmailId.text = Constants.USER_EMAILID

        Utility.setSemibold(this,headeName)
        Utility.setRegular(this,headerEmailId)

        headeImage = header.findViewById(R.id.user_imageView)
        /*Glide.with(this).load(Constants.USER_IMAGE_URL)
            .into(headeImage!!);
        setSupportActionBar(toolbar)*/
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController=navHostFragment.navController
        // appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.settingsFragment,R.id.notificationFragment),drawer_layout)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.calanderFragment, R.id.charityFragment, R.id.profileFragment), drawerLayout)
        //setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnNavigationItemReselectedListener {
            // Do nothing to ignore the reselection
        }

        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(this);

        getActionBar()?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setHomeButtonEnabled(false); // disable the button
        actionBar?.setDisplayHomeAsUpEnabled(false); // remove the left caret
        actionBar?.setDisplayShowHomeEnabled(false); // remove the icon
        navImage!!.setOnClickListener {

            // If the navigation drawer is not open then open it, if its already open then close it.
            // If the navigation drawer is not open then open it, if its already open then close it.
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) drawerLayout.closeDrawer(Gravity.RIGHT) else drawerLayout.openDrawer(
                    Gravity.RIGHT
            )

            //navController.navigateUp() || super.onSupportNavigateUp()
            Log.e("navigation", onSupportNavigateUp().toString())

        }






        headeName?.setOnClickListener {

            drawerLayout.closeDrawers();
            navController.navigate(R.id.profileFragment)
        }

        headeImage?.setOnClickListener {
            drawerLayout.closeDrawers();

            navController.navigate(R.id.profileFragment)
        }

        Utility.setbold(this,screen_name)

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

       Log.e("item_id", item.itemId.toString())
        item.setChecked(true);

        drawerLayout.closeDrawers();
        when (item.itemId) {
            R.id.charityFragment -> navController.navigate(R.id.charityFragment)
            R.id.calanderFragment -> navController.navigate(R.id.calanderFragment)
            R.id.homeFragment -> {

                Log.e("Here in navigation click","Here in navigation click");
                navController.navigate(R.id.homeFragment)

            }

                R.id.profileFragment -> navController.navigate(R.id.profileFragment)
            R.id.changePasswordFragment -> navController.navigate(R.id.changePasswordFragment)
            R.id.loginActivity -> logout()/*navController.navigate(R.id.changePasswordFragment)*/



        }
        return true;


    }

    private fun logout() {


        val preferences = getSharedPreferences("com.mexcelle.whitelable", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
        finish()
        val i = Intent(applicationContext, LoginActivity::class.java)
        startActivity(i)

    }


}