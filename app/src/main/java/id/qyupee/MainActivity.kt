package id.qyupee

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import id.qyupee.ui.auth.AuthActivity
import id.qyupee.ui.base.BaseActivity
import id.qyupee.ui.cart.CartActivity
import id.qyupee.ui.feed_add.FeedAddActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_left_menu.*
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {
    private var slideX: Float = 0.0f
    private var nextFragment: Int? = null

    override fun setLayoutResource(): Int = R.layout.activity_main

    override fun initActivity() {
        //Check login or not
        if (getPreference().token == null) {
            startActivity<AuthActivity>()
            finish()
        }

        val actionBarDrawerToggle =
            object : ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    slideX = drawerView.width * slideOffset
                    content.translationX = slideX

                    if (slideX == 0.0f) {
                        if (nextFragment != null) loadFragment(nextFragment!!)
                        nextFragment = null
                    } else {

                    }
                }
            }

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
    }

    override fun onViewReady() {
        llDashboard.onClick {
            nextFragment(R.id.dashboardFragment)
            setSelectedMenu(llDashboard.id)
        }
        llFeed.onClick {
            nextFragment(R.id.feedFragment)
            setSelectedMenu(llFeed.id)
        }
        llProduct.onClick {
            nextFragment(R.id.productFragment)
            setSelectedMenu(llProduct.id)
        }
        llReport.onClick {
            nextFragment(R.id.orderFragment)
            setSelectedMenu(llReport.id)
        }
        llLogout.onClick { getPreference().logout(this@MainActivity) }
        tv_create_payment.onClick { startActivity<CartActivity>() }
        tv_add_feed.onClick { startActivity<FeedAddActivity>() }

        tvUserName.text = getPreference().userData.nama

        Log.d("TEST", getPreference().userData.toString())
    }

    private fun setSelectedMenu(id: Int) {
        dashboardIcon.image = ContextCompat.getDrawable(this, R.drawable.ic_dashboard)
        feedIcon.image = ContextCompat.getDrawable(this, R.drawable.ic_feed)
        productIcon.image = ContextCompat.getDrawable(this, R.drawable.ic_product)
        reportIcon.image = ContextCompat.getDrawable(this, R.drawable.ic_report)

        when (id) {
            llDashboard.id -> dashboardIcon.image =
                ContextCompat.getDrawable(this, R.drawable.ic_dashboard_active)
            llFeed.id -> feedIcon.image = ContextCompat.getDrawable(this, R.drawable.ic_feed_active)
            llProduct.id -> productIcon.image =
                ContextCompat.getDrawable(this, R.drawable.ic_product_active)
            llReport.id -> reportIcon.image =
                ContextCompat.getDrawable(this, R.drawable.ic_report_active)
        }
    }

    private fun nextFragment(fragment: Int = R.id.dashboardFragment) {
        nextFragment = fragment
        drawerLayout.closeDrawers()
    }

    private fun loadFragment(fragment: Int = R.id.dashboardFragment) {
        findNavController(R.id.nav_host_fragment).navigate(fragment)
    }

    fun showDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }
}
