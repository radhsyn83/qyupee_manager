package id.qyupee.ui.feed


import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.github.ybq.android.spinkit.SpinKitView
import id.qyupee.R
import id.qyupee.adapter.FeedAdapter
import id.qyupee.model.feed.Feed
import id.qyupee.ui.base.RecyclerFragment
import id.qyupee.ui.feed_add.FeedAddActivity
import kotlinx.android.synthetic.main.fragment_feed.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivityForResult
import java.util.*

class FeedFragment : RecyclerFragment() {

    private lateinit var viewModel: FeedViewModel
    private lateinit var feedAdapter: FeedAdapter

    private var firstLoad = true

    override fun setLayoutResource(): Int = R.layout.fragment_feed

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
            viewModelFactory { FeedViewModel(ctx, getPreference().token) })
            .get(FeedViewModel::class.java)

        viewModel.data.observe(this, Observer {
            isRefresh(false)
            updateRecyclerView(it ?: arrayListOf())
        })

        viewModel.status.observe(this, Observer {
            isRefresh(it)
        })

        //COMPONENTS
        iv_add.onClick { startActivityForResult<FeedAddActivity>(100) }
        feedAdapter = FeedAdapter(ctx, null)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = feedAdapter
            visibility = View.GONE
        }
    }

    override fun onViewReady() {
        viewModel.load()
    }

    private fun updateRecyclerView(data: ArrayList<Feed.Data>) {
        feedAdapter.addItem(data, firstLoad)

        //ANIMATION
        if (firstLoad) {
            firstLoad = false
            YoYo.with(Techniques.FadeIn)
                .repeat(0)
                .duration(300)
                .delay(1000)
                .playOn(recyclerView)
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            firstLoad = true
            viewModel.load()
        }
    }

}
