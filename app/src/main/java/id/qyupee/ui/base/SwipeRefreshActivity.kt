package id.qyupee.ui.base

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.qyupee.R
import id.qyupee.utils.UserPreference
import org.jetbrains.anko.sdk27.coroutines.onClick

//
// Created by Fathur Radhy 
// on June 2019-06-06.
//

abstract class SwipeRefreshActivity : AppCompatActivity() {
    private lateinit var userPreference: UserPreference
    private var swipeRefresh: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutResource())

        //UserPreference
        userPreference = UserPreference(PreferenceManager
            .getDefaultSharedPreferences(this))

//        findViewById<ImageView>(R.id.iv_back)?.onClick {
//            finish()
//        }

        swipeRefresh = setSwipeRefresh()

        initActivity()
        onViewReady()

        if (swipeRefresh != null) {
            swipeRefresh?.setOnRefreshListener {
                onPullRefresh()
            }
        }
    }

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
        }

    protected abstract fun setLayoutResource() : Int
    protected abstract fun setSwipeRefresh() : SwipeRefreshLayout
    protected abstract fun initActivity()
    protected abstract fun onViewReady()
    protected abstract fun onPullRefresh()

    protected fun getPreference() : UserPreference {
        return userPreference
    }

    fun isRefresh(status: Boolean) {
        swipeRefresh?.isRefreshing = status
    }
}