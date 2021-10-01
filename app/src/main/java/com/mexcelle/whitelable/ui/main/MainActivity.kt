package com.mexcelle.whitelable.ui.main

import android.R.id
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

class MainActivity : AppCompatActivity() ,NavigationView.OnNavigationItemSelectedListener{

    private  lateinit var navController: NavController
    private  lateinit var appBarConfiguration: AppBarConfiguration
    private  lateinit var toolbar: Toolbar
    private  lateinit var bottomNavigationView: BottomNavigationView
    private  lateinit var navigationView: NavigationView
    private  lateinit var drawerLayout: DrawerLayout
    private  lateinit var navImage: ImageView

    companion object{
        lateinit var screenName:TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        screenName = findViewById(R.id.screen_name)
        navImage = toolbar!!.findViewById(R.id.nav_btn)

        bottomNavigationView=findViewById(R.id.bottum_id)
        navigationView=findViewById(R.id.navigation_id)
        drawerLayout=findViewById(R.id.drawer_layout)

        setSupportActionBar(toolbar)
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        navController=navHostFragment.navController
        // appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.settingsFragment,R.id.notificationFragment),drawer_layout)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.calanderFragment, R.id.charityFragment, R.id.profileFragment), drawerLayout)

        //setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
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
            R.id.homeFragment -> navController.navigate(R.id.homeFragment)
            R.id.profileFragment -> navController.navigate(R.id.profileFragment)
            R.id.changePasswordFragment -> navController.navigate(R.id.changePasswordFragment)
        }
        return true;


    }


}