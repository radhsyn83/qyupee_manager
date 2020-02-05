package id.qyupee.ui.product


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
import id.qyupee.adapter.ProductAdapter
import id.qyupee.model.product.Product
import id.qyupee.ui.base.RecyclerFragment
import kotlinx.android.synthetic.main.fragment_product.*
import org.jetbrains.anko.support.v4.ctx

class ProductFragment : RecyclerFragment() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter

    private var firstLoad = true

    override fun setLayoutResource(): Int = R.layout.fragment_product

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
            viewModelFactory { ProductViewModel(ctx, getPreference().token) })
            .get(ProductViewModel::class.java)

        viewModel.data.observe(this, Observer {
            isRefresh(false)
            updateRecyclerView(it ?: arrayListOf())
        })

        viewModel.status.observe(this, Observer {
            isRefresh(it)
        })

        //COMPONENTS
        productAdapter = ProductAdapter(ctx, null)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = productAdapter
            visibility = View.GONE
        }
    }

    override fun onViewReady() {
        viewModel.load()
    }

    private fun updateRecyclerView(data: ArrayList<Product.Data>) {
        productAdapter.addItem(data, firstLoad)

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


}
