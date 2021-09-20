package com.mexcelle.whitelable.ui.onboardingscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.mexcelle.whitelable.R
import com.mexcelle.whitelable.ui.login.LoginActivity
import com.mexcelle.whitelable.ui.main.MainActivity
import com.mexcelle.whitelable.ui.onboardingscreen.adapter.ImageAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import java.util.*

class OnBoardingActivity : AppCompatActivity() {

    private val mPager: ViewPager? = null
    private var currentPage = 0
    private var NUM_PAGES = 0
    private val IMAGES = arrayOf(R.drawable.image_one, R.drawable.image_two, R.drawable.image_third)
    private val ImagesArray = ArrayList<Int>()
    private var mPager11: ViewPager? = null
    var titleTv: TextView? = null
    var subtitleTv: TextView? = null
    var nextImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        init()

    }

     fun init() {
        for (i in IMAGES.indices) ImagesArray.add(
            IMAGES.get(i)
        )
        mPager11 = findViewById<View>(R.id.pager11) as ViewPager
        val skip = findViewById<View>(R.id.skip_btn) as TextView

         titleTv = findViewById(R.id.title1)
         subtitleTv = findViewById(R.id.title2)
         nextImage = findViewById(R.id.next_id)


        skip.setOnClickListener {
            gotoLoginPage()
        }


         nextImage!!.setOnClickListener {
             gotoLoginPage()
         }

        mPager11!!.setAdapter(ImageAdapter(this@OnBoardingActivity, ImagesArray))
        val indicator = findViewById<View>(R.id.indicator) as DotsIndicator
        indicator.setViewPager(mPager11!!)
        val density = resources.displayMetrics.density

        //Set circle indicator radius
       // indicator.radius = 5 * density
        NUM_PAGES = IMAGES.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager11!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 2000, 2000)

        // Pager listener over indicator


         mPager11!!.addOnPageChangeListener(object : OnPageChangeListener {
             override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
             }

             override fun onPageSelected(position: Int) {
                 currentPage = position
                 if (position == 0) {
                     titleTv!!.setText("Volunteer for different \ncause")
                     subtitleTv!!.setText("90's prism food truck aesthetic twee man braid austin banjo photo booth tofu poke chillwave master cleanse Succulents marfa truffaut distillery church-key four loko. Prism hashtag brunch 90's chambray, meditation fanny pack tilde messenger bag squid.")
                 }
                 if (position == 1) {
                     titleTv!!.setText("Meet new and alike \npeople")
                     subtitleTv!!.setText("90's prism food truck aesthetic twee man braid austin banjo photo booth tofu poke chillwave master cleanse Succulents marfa truffaut distillery church-key four loko. Prism hashtag brunch 90's chambray, meditation fanny pack tilde messenger bag squid.")
                 }
                 if (position == 2) {
                     titleTv!!.setText("Make the world a \nbetter place")
                     subtitleTv!!.setText("90's prism food truck aesthetic twee man braid austin banjo photo booth tofu poke chillwave master cleanse Succulents marfa truffaut distillery church-key four loko. Prism hashtag brunch 90's chambray, meditation fanny pack tilde messenger bag squid.")
                 }
             }
             override fun onPageScrollStateChanged(state: Int) {

             }
         })
     /*
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                currentPage = position
                if (position == 0) {
                    titleTv!!.setText("Volunteer for different \ncause")
                    subtitleTv!!.setText("90's prism food truck aesthetic twee man braid austin banjo photo booth tofu poke chillwave master cleanse Succulents marfa truffaut distillery church-key four loko. Prism hashtag brunch 90's chambray, meditation fanny pack tilde messenger bag squid.")
                }
                if (position == 1) {
                    titleTv!!.setText("Meet new and alike \npeople")
                    subtitleTv!!.setText("90's prism food truck aesthetic twee man braid austin banjo photo booth tofu poke chillwave master cleanse Succulents marfa truffaut distillery church-key four loko. Prism hashtag brunch 90's chambray, meditation fanny pack tilde messenger bag squid.")
                }
                if (position == 2) {
                    titleTv!!.setText("Make the world a \nbetter place")
                    subtitleTv!!.setText("90's prism food truck aesthetic twee man braid austin banjo photo booth tofu poke chillwave master cleanse Succulents marfa truffaut distillery church-key four loko. Prism hashtag brunch 90's chambray, meditation fanny pack tilde messenger bag squid.")
                }
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(pos: Int) {}
        })
*/
    }

    private fun gotoLoginPage() {

        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
    }

}