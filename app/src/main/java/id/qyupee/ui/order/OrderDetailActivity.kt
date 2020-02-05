package id.qyupee.ui.order

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.qyupee.R
import id.qyupee.adapter.OrderDetailAdapter
import id.qyupee.adapter.VariantAdapter
import id.qyupee.model.order.OrderDetail
import id.qyupee.model.product.VariantList
import id.qyupee.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_variant.*
import org.jetbrains.anko.ctx

class OrderDetailActivity : BaseActivity(), VariantAdapter.Listener {

    private lateinit var viewModel: OrderViewModel
    private lateinit var detailAdapter: OrderDetailAdapter

    override fun setLayoutResource(): Int = R.layout.activity_order_detail

    override fun initActivity() {
        //VIEWMODEL
        viewModel = ViewModelProviders.of(this,
            viewModelFactory { OrderViewModel(this, getPreference().token) })
            .get(OrderViewModel::class.java)
        viewModel.detail.observe(this, Observer {
            updateRecyclerView(it ?: arrayListOf())
        })
        //COMPONENTS
        detailAdapter = OrderDetailAdapter(this, null)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = detailAdapter
        }
    }

    override fun onViewReady() {
        val idOder = intent.extras?.getString("idOder").toString()
        viewModel.detail(idOder)
    }

    private fun updateRecyclerView(data: ArrayList<OrderDetail.Data>) {
        detailAdapter.addItem(data)
    }

    override fun onItemClicked(data: VariantList.Data) {
        val i = Intent()
        i.putExtra("variant", data)
        setResult(Activity.RESULT_OK, i)
        finish()
    }

}

