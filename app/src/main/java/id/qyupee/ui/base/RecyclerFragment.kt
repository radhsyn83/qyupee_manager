package id.qyupee.ui.base

import android.os.Build
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ybq.android.spinkit.SpinKitView
import id.qyupee.MainActivity
import id.qyupee.utils.UserPreference
import id.qyupee.R
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx

//
// Created by Fathur Radhy 
// on May 2019-05-29.
//
abstract class RecyclerFragment : Fragment() {
    private lateinit var userPreference: UserPreference
    private var swipeRefresh: SwipeRefreshLayout? = null
    private var loadMore: SpinKitView? = null
    private var nestedScrollView: NestedScrollView? = null

    var isEnableLoadMore = true

    private var mView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null) {
            mView = inflater.inflate(setLayoutResource(), container, false)
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //UserPreference
        userPreference = UserPreference(PreferenceManager.getDefaultSharedPreferences(ctx))

        //Init Components
        nestedScrollView = setNestedScrollView()
        swipeRefresh = setSwipeRefresh()
        loadMore = setLoadMore()

        mView?.findViewById<ImageView>(R.id.iv_menu)?.onClick {
            (context as MainActivity).showDrawer()
        }

        //Set Swipe Refresh
        if (swipeRefresh != null) {
            swipeRefresh?.setOnRefreshListener {
                onPullRefresh()
            }
        }

        if (nestedScrollView != null) {
            nestedScrollView?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (isEnableLoadMore && (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight))) {
                    //BOTTOM REACH END
                    showLoadMore(true)
                    nestedScrollView?.post { nestedScrollView?.fullScroll(View.FOCUS_DOWN) }

                    onLoadDataMore()
                }
            })
        }

//        mView?.findViewById<ImageView>(R.id.iv_back)?.onClick {
//            findNavController().navigateUp()
//        }
//
//        mView?.findViewById<ImageView>(R.id.iv_menu)?.onClick {
//            (context as MainActivity).showDrawer()
//        }

        initFragment()
        onViewReady()
    }

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

    protected abstract fun setLayoutResource(): Int
    protected abstract fun setNestedScrollView(): NestedScrollView
    protected abstract fun setSwipeRefresh(): SwipeRefreshLayout
    protected abstract fun setLoadMore(): SpinKitView
    protected abstract fun initFragment()
    protected abstract fun onViewReady()
    protected abstract fun onPullRefresh()
    protected abstract fun onLoadDataMore()

    protected fun getPreference(): UserPreference {
        return userPreference
    }

    fun isRefresh(status: Boolean) {
        swipeRefresh?.isRefreshing = status
    }

    fun showLoadMore(status: Boolean) {
        if (status)
            loadMore?.visibility = View.VISIBLE
        else
            loadMore?.visibility = View.GONE
    }
}