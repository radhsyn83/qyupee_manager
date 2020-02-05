package id.qyupee.ui.cart

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.geolo.library.taggroup.GeoloTagGroup
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.segamedev.bandros.BMoney
import id.qyupee.BuildConfig
import id.qyupee.R
import id.qyupee.adapter.CartRoomAdapter
import id.qyupee.adapter.ProductCartAdapter
import id.qyupee.model.product.Product
import id.qyupee.model.product.Variant
import id.qyupee.room.CartEntity
import id.qyupee.ui.base.SwipeRefreshActivity
import id.qyupee.ui.checkout.CheckoutActivity
import id.qyupee.ui.product.ProductViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class CartActivity : SwipeRefreshActivity(), CartRoomAdapter.Listener, ProductCartAdapter.Listener,
    TextWatcher {

    private lateinit var roomViewModel: CartRoomViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartAdapter: CartRoomAdapter
    private lateinit var productCartAdapter: ProductCartAdapter
    private lateinit var variants: ArrayList<Variant.Data>

    private var cartTotalQty = 0
    private var cartTotalPrice = 0
    private var totalPrice = 0
    private var qty = 1
    private var firstLoad = true

    private var variant = ""
    private var selectedMarkup: Int? = null
    private var selectedVariant: Variant.Data? = null

    override fun setLayoutResource(): Int = R.layout.activity_cart

    override fun setSwipeRefresh(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initActivity() {
        //Cart
        roomViewModel = ViewModelProviders.of(this).get(CartRoomViewModel::class.java)
        roomViewModel.all.observe(this, Observer { res ->
            showVariant(false)
            updateCartRecyclerView(res)
        })

        //Product
        productViewModel = ViewModelProviders.of(this,
            viewModelFactory { ProductViewModel(this, getPreference().token) })
            .get(ProductViewModel::class.java)
        productViewModel.data.observe(this, Observer {
            isRefresh(false)
            updateProductRecyclerView(it ?: arrayListOf())
        })
        productViewModel.status.observe(this, Observer {
            isRefresh(it)
        })
        productViewModel.variant.observe(this, Observer {
            updateVariant(it ?: arrayListOf())
        })
        productViewModel.statusVariant.observe(this, Observer {
            loadingVariant(it)
        })

        //RV CART
        cartAdapter = CartRoomAdapter(this, this)
        rv_cart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity, RecyclerView.VERTICAL, false)
            adapter = cartAdapter
        }

        rv_checkout.onClick {
            if (tvCartGrandTotal.text != "Rp0") {
                startActivityForResult<CheckoutActivity>(100)
            }
        }

        //RV PRODUCT
        productCartAdapter = ProductCartAdapter(this, this)
        rv_product.apply {
            layoutManager = GridLayoutManager(this@CartActivity, 3, RecyclerView.VERTICAL, false)
            adapter = productCartAdapter
        }

        //INIT COMPONENTS
        initComponents()
    }

    private fun initComponents() {
        //Init View
        cvCart.visibility = View.VISIBLE
        bt_add_cart.visibility = View.GONE
        llPecentage.visibility = View.GONE
        llPrice.visibility = View.GONE
        llPriceManual.visibility = View.GONE
        cvVariant.visibility = View.GONE
        et_manual_price.addTextChangedListener(this)
        et_pecentage.addTextChangedListener(this)

        //Add cart
        bt_add_cart.onClick { addToCart() }

        //clear cart
        iv_trash.onClick {
            roomViewModel.nukeTable()
            cartAdapter.clear()
        }

        //Close Variant
        iv_close_variant.bringToFront()
        iv_close_variant.onClick {
            showVariant(false)
        }

        //Update cart qty and price
        tvCartQty.text = getString(R.string.format_items_cart, cartTotalQty.toString())
        tvCartGrandTotal.text = BMoney.toRupiah(cartTotalPrice.toString())

        //Minus
        iv_minus.onClick {
            if (qty != 1) qty -= 1
            tvSelectedQty.text = qty.toString()
            updateVariantPrice()
        }
        iv_plus.onClick {
            qty += 1
            tvSelectedQty.text = qty.toString()
            updateVariantPrice()
        }

        //Set markup
        val markupItem = arrayListOf<String>()
        resources.getStringArray(R.array.markup).forEach {
            markupItem.add(it)
        }
        tag_markup.setTags(markupItem as List<String>)
        tag_markup.setOnTagChangeListener(object : GeoloTagGroup.OnTagChangeListener {
            override fun onDelete(geoloTagGroup: GeoloTagGroup?, tag: String?) {}
            override fun onAppend(geoloTagGroup: GeoloTagGroup?, tag: String?) {}
            override fun onCheckedChanged(
                geoloTagGroup: GeoloTagGroup?,
                tag: String?,
                isChecked: Boolean
            ) {
                resources.getStringArray(R.array.markup).forEachIndexed { index, s ->
                    if (tag == s) {
                        selectedMarkup = index
                        updateVariantPrice()
                    }
                }
            }

        })
    }

    private fun addToCart() {
        if (selectedVariant != null && selectedMarkup != null) {
            val idVariant = selectedVariant!!.id
            val sku = selectedVariant!!.sku
            val ukuran = selectedVariant!!.ukuran
            val warna = selectedVariant!!.warna
            val cart = CartEntity(null, idVariant, totalPrice, qty, selectedMarkup!!, sku, warna, ukuran)
            roomViewModel.insert(cart)
            cartAdapter.addItem(cart)
            Handler().postDelayed({
                rv_cart.scrollToPosition(0)
            },100)
        }
    }

    override fun onViewReady() {
        productViewModel.load()
    }

    private fun updateProductRecyclerView(product: ArrayList<Product.Data>) {
        productCartAdapter.addItem(product)
    }

    private fun updateCartRecyclerView(cart: List<CartEntity>?) {
        cartTotalQty = 0
        cartTotalPrice = 0
        val cartData = cart ?: arrayListOf()

        if (cartData.isNotEmpty()) {
            variant = ""
            cart?.forEachIndexed { i, d ->
                //Addvariant
                variant += if (i < (cart.size - 1)) d.idVariant + "," else d.idVariant
                cartTotalQty += d.qty
                cartTotalPrice += d.hargaJual

                if (firstLoad)
                    cartAdapter.addItem(d)
            }
        } else {
            cartAdapter.clear()
        }

        //Update cart qty and price
        tvCartQty.text = getString(R.string.format_items_cart, cartTotalQty.toString())
        tvCartGrandTotal.text = BMoney.toRupiah(cartTotalPrice.toString())

        firstLoad = false
    }

    override fun onCartPlus(data: CartEntity) {
    }

    override fun onCartMinus(data: CartEntity) {
    }

    override fun onCartDelete(data: CartEntity, pos: Int) {
        roomViewModel.delete(data)
        cartAdapter.deleteItem(pos)
        roomViewModel.getAll()
    }

    override fun onProductClick(data: Product.Data) {
        clearData()
        showVariant(true)
        productViewModel.loadVariant(data.id)
    }

    private fun showVariant(isShow: Boolean) {
        if (isShow) {
            cvCart.visibility = View.GONE
            cvVariant.visibility = View.VISIBLE
        } else {
            cvCart.visibility = View.VISIBLE
            cvVariant.visibility = View.GONE
        }
    }

    private fun loadingVariant(isLoading: Boolean) {
        if (isLoading) {
            svVariant.alpha = 0.0F
            llVariant.alpha = 1.0F
        } else {
            llVariant.alpha = 0.0F
            YoYo.with(Techniques.FadeIn)
                .repeat(0)
                .duration(300)
                .playOn(svVariant)
        }
    }

    private fun updateVariant(variants: ArrayList<Variant.Data>) {
        this.variants = variants
        val tagItemSize = arrayListOf<String>()
        this.variants.forEach {
            tagItemSize.add(it.ukuran)
        }
        tag_size.setTags(tagItemSize as List<String>)
        tag_size.setOnTagChangeListener(object : GeoloTagGroup.OnTagChangeListener {
            override fun onDelete(geoloTagGroup: GeoloTagGroup?, tag: String?) {}
            override fun onAppend(geoloTagGroup: GeoloTagGroup?, tag: String?) {}
            override fun onCheckedChanged(
                geoloTagGroup: GeoloTagGroup?,
                tag: String?,
                isChecked: Boolean
            ) {
                variants.forEach {
                    if (tag == it.ukuran) {
                        selectedVariant = it
                        updateVariantPrice()
                    }
                }
            }
        })
    }

    private fun updateVariantPrice() {
        if (selectedVariant != null && selectedMarkup != null) {
            when (selectedMarkup) {
                1 -> {
                    val percent =
                        if (et_pecentage.text.toString() == "") 0 else et_pecentage.text.toString().toInt()
                    val profit = ((selectedVariant?.hargaProduksi ?: 0) * percent) / 100
                    totalPrice = ((selectedVariant?.hargaProduksi ?: 0) + profit) * qty
                    llPecentage.visibility = View.VISIBLE
                    llPriceManual.visibility = View.GONE
                }
                2 -> {
                    totalPrice = (selectedVariant?.hargaProduksi ?: 0) * qty
                    llPecentage.visibility = View.GONE
                    llPriceManual.visibility = View.GONE
                }
                3 -> {
                    val price = if (et_manual_price.text.toString() == "") 0 else et_manual_price.text.toString().toInt()
                    totalPrice = price * tvSelectedQty.text.toString().toInt()
                    llPecentage.visibility = View.GONE
                    llPriceManual.visibility = View.VISIBLE
                }
                else -> {
                    totalPrice = selectedVariant!!.hargaJual * qty
                    llPecentage.visibility = View.GONE
                }
            }
            llPrice.visibility = View.VISIBLE
            bt_add_cart.visibility = View.VISIBLE
            tv_price.text = BMoney.toRupiah(totalPrice.toString())
        } else {
            bt_add_cart.visibility = View.GONE
            llPrice.visibility = View.GONE
        }
    }

    private fun clearData() {
        totalPrice = 0
        selectedMarkup = null
        selectedVariant = null
        tag_markup.setCheckedTags(-1)
        tag_size.setCheckedTags(-1)
        bt_add_cart.visibility = View.GONE
        llPrice.visibility = View.GONE
    }

    override fun afterTextChanged(s: Editable?) {
        if (selectedMarkup == 1 || selectedMarkup == 3) {
            updateVariantPrice()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun onPullRefresh() {
        productViewModel.load()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            roomViewModel.nukeTable()
            roomViewModel.all
        }
    }
}
