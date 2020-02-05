package id.qyupee.ui.checkout

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import id.qyupee.R
import id.qyupee.room.CartEntity
import id.qyupee.ui.base.BaseActivity
import id.qyupee.ui.cart.CartRoomViewModel
import kotlinx.android.synthetic.main.activity_checkout.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CheckoutActivity : BaseActivity() {

    private lateinit var viewModel: CheckoutViewModel
    private lateinit var roomViewModel: CartRoomViewModel
    private lateinit var cart: List<CartEntity>

    override fun setLayoutResource(): Int = R.layout.activity_checkout

    override fun initActivity() {
        //Cart
        roomViewModel = ViewModelProviders.of(this).get(CartRoomViewModel::class.java)
        roomViewModel.all.observe(this, Observer { res ->
            cart = res
        })

        //VIEWMODEL
        viewModel = ViewModelProviders.of(this,
            viewModelFactory { CheckoutViewModel(this, getPreference().token) })
            .get(CheckoutViewModel::class.java)

        viewModel.data.observe(this, Observer {
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(it.msg.toString())
                .setContentText("Transaksi berhasil.")
                .setConfirmClickListener {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                .show()
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                getLoading().show()
            } else {
                getLoading().dismiss()
            }
        })
    }

    override fun onViewReady() {
        bt_checkout.onClick {
            if (et_customer.text.isNotEmpty()) {
                var st = 1
                val idVariant = generateValueJSON(1)
                val hargaJual = generateValueJSON(2)
                val qty = generateValueJSON(3)
                if (rgType.checkedRadioButtonId == R.id.rbPending) {
                    st = 2
                }

                viewModel.add(
                    "1",
                    et_customer.text.toString(),
                    st.toString(),
                    idVariant,
                    hargaJual,
                    qty
                )
            }
        }
    }

    private fun generateValueJSON(type: Int): String {
        var r = ""
        cart.forEachIndexed { i, v ->
            val d = when (type) {
                1 -> v.idVariant
                2 -> v.hargaJual
                else -> v.qty
            }
            if (i != (cart.size - 1)) {
                r += "$d,"
            } else {
                r += d
            }
        }

        return r
    }
}
