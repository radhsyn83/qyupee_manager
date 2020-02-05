package id.qyupee.ui.product

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.qyupee.R
import id.qyupee.adapter.VariantAdapter
import id.qyupee.model.product.VariantList
import id.qyupee.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_variant.*
import org.jetbrains.anko.ctx

class VariantActivity : BaseActivity(), VariantAdapter.Listener {

    private lateinit var viewModel: ProductViewModel
    private lateinit var variantAdapter: VariantAdapter

    override fun setLayoutResource(): Int = R.layout.activity_variant

    override fun initActivity() {
        //VIEWMODEL
        viewModel = ViewModelProviders.of(this,
            viewModelFactory { ProductViewModel(this, getPreference().token) })
            .get(ProductViewModel::class.java)
        viewModel.variantList.observe(this, Observer {
            updateRecyclerView(it ?: arrayListOf())
        })
        //COMPONENTS
        variantAdapter = VariantAdapter(this, this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = variantAdapter
        }
    }

    override fun onViewReady() {
        viewModel.loadVariant()
    }

    private fun updateRecyclerView(data: ArrayList<VariantList.Data>) {
        variantAdapter.addItem(data)
    }

    override fun onItemClicked(data: VariantList.Data) {
        val i = Intent()
        i.putExtra("variant", data)
        setResult(Activity.RESULT_OK, i)
        finish()
    }

}
