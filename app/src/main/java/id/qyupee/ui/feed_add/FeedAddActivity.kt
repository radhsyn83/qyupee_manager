package id.qyupee.ui.feed_add

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.segamedev.bandros.BMoney
import id.qyupee.BuildConfig
import id.qyupee.R
import id.qyupee.model.product.VariantList
import id.qyupee.ui.base.BaseActivity
import id.qyupee.ui.feed.FeedViewModel
import id.qyupee.ui.product.VariantActivity
import kotlinx.android.synthetic.main.activity_feed_add.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.toast

class FeedAddActivity : BaseActivity(), TextWatcher {
    private lateinit var viewModel: FeedViewModel
    private var selectedVariant: VariantList.Data? = null
    private lateinit var dialog: SweetAlertDialog

    var totalPrice = 0

    override fun setLayoutResource(): Int = R.layout.activity_feed_add

    override fun initActivity() {
        //Dialog
        dialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setContentText(getString(R.string.LOADING))

        //VIEWMODEL
        viewModel = ViewModelProviders.of(this,
            viewModelFactory { FeedViewModel(this, getPreference().token) })
            .get(FeedViewModel::class.java)

        viewModel.addFeed.observe(this, Observer {
            finish()
            setResult(Activity.RESULT_OK)
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        })

        //Status Type init
        rgType.onCheckedChange { group, checkedId ->
            when(checkedId){
                R.id.rbRestock -> {
                    llIncome.visibility = View.GONE
                    YoYo.with(Techniques.FadeIn)
                        .repeat(0)
                        .duration(300)
                        .playOn(llRestock)
                    llRestock.visibility = View.VISIBLE
                }
                else -> {
                    llRestock.visibility = View.GONE
                    YoYo.with(Techniques.FadeIn)
                        .repeat(0)
                        .duration(300)
                        .playOn(llIncome)
                    llIncome.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onViewReady() {
        et_count.addTextChangedListener(this)
        tv_variant.onClick { startActivityForResult<VariantActivity>(100) }
        btAdd.onClick { addFeed() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            selectedVariant = data?.getParcelableExtra("variant")
            selectedVariant.let {
                tv_variant.text = getString(R.string.format_selected_variant,
                    it?.kode, it?.ukuran, BMoney.toRupiah(it?.hargaProduksi.toString()))
            }
            updateForm()
        }
    }

    private fun updateForm() {
        selectedVariant?.let {
            var qty = 0
            if (!et_count.text.isNullOrEmpty()) {
                qty = et_count.text.toString().toInt()
            }
            totalPrice = qty * it.hargaProduksi
            tv_total.text = BMoney.toRupiah(totalPrice.toString())
        }
    }

    override fun afterTextChanged(s: Editable?) {
        updateForm()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    private fun addFeed() {
        when(rgType.checkedRadioButtonId) {
            R.id.rbRestock -> {
                selectedVariant?.let {
                    viewModel.add("1", getString(R.string.format_note_restock, it.nama), totalPrice.toString(), et_count.text.toString(), it.id)
                }
            }
            R.id.rbIncome -> {
                viewModel.add("2", et_note.text.toString(), et_nominal.text.toString())
            }
            else -> {
                viewModel.add("3", et_note.text.toString(), et_nominal.text.toString())
            }
        }
    }
}
