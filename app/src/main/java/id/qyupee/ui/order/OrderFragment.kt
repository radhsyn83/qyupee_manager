package id.qyupee.ui.order


import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ybq.android.spinkit.SpinKitView
import id.qyupee.R
import id.qyupee.adapter.FeedAdapter
import id.qyupee.adapter.OrderAdapter
import id.qyupee.model.order.Order
import id.qyupee.ui.base.RecyclerFragment
import id.qyupee.ui.feed_add.FeedAddActivity
import kotlinx.android.synthetic.main.fragment_feed.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import java.util.*

class OrderFragment : RecyclerFragment(), OrderAdapter.Listener {

    private lateinit var viewModel: OrderViewModel
    private lateinit var orderAdapter: OrderAdapter

    private var firstLoad = true

    override fun setLayoutResource(): Int = R.layout.fragment_order

    override fun setNestedScrollView(): NestedScrollView = nestedScrollView

    override fun setSwipeRefresh(): SwipeRefreshLayout = swipeRefreshLayout

    override fun setLoadMore(): SpinKitView = spinKit

    override fun onPullRefresh() {
        firstLoad = true
        viewModel.load()
    }

    override fun onLoadDataMore() {
    }

    override fun initFragment() {
        //VIEWMODEL
        viewModel = ViewModelProviders.of(this,
            viewModelFactory { OrderViewModel(ctx, getPreference().token) })
            .get(OrderViewModel::class.java)

        viewModel.data.observe(this, Observer {
            isRefresh(false)
            updateRecyclerView(it ?: arrayListOf())
        })

        viewModel.status.observe(this, Observer {
            isRefresh(it)
        })

        //COMPONENTS
        iv_add.onClick { startActivityForResult<FeedAddActivity>(100) }
        orderAdapter = OrderAdapter(ctx, this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, true)
            adapter = orderAdapter
        }
    }

    override fun onViewReady() {
        viewModel.load()
    }

    private fun updateRecyclerView(data: ArrayList<Order.Data>) {
        orderAdapter.addItem(data, firstLoad)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            firstLoad = true
            viewModel.load()
        }
    }

    override fun onItemClicked(data: Order.Data, itemView: View) {
        startActivity<OrderDetailActivity>("idOder" to data.id.toString())
    }
}
