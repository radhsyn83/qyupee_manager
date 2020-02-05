package id.qyupee.ui.dashboard

import id.qyupee.R
import id.qyupee.ui.base.BaseFragment
import id.qyupee.ui.feed_add.FeedAddActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity

class DashboardFragment : BaseFragment() {

    override fun setLayoutResource(): Int = R.layout.fragment_dashboard

    override fun initFragment() {
        swipeRefreshLayout.isEnabled = false
    }

    override fun onViewReady() {
    }

}
