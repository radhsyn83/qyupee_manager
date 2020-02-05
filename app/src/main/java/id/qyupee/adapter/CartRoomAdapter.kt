package id.qyupee.adapter

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.segamedev.bandros.BMoney
import id.qyupee.R
import id.qyupee.room.CartEntity
import kotlinx.android.synthetic.main.item_cart.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick


//
// Created by Fathur Radhy 
// on May 2019-05-28.
//

class CartRoomAdapter(
    val context: Context,
    val listener: Listener
) : RecyclerView.Adapter<CartRoomAdapter.ViewHolder>() {

    private var data = arrayListOf<CartEntity>()

    interface Listener {
        fun onCartPlus(data: CartEntity)
        fun onCartMinus(data: CartEntity)
        fun onCartDelete(data: CartEntity, pos: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: CartEntity, pos: Int) {
            val markupItem = context.resources.getStringArray(R.array.markup)

            itemView.llContent.visibility = View.VISIBLE
            itemView.tv_qty.text = item.qty.toString()
            itemView.tv_code.text = item.sku
            itemView.tv_metode.text = markupItem[item.status]
            itemView.tv_variant.text = context.getString(
                R.string.format_size_color,
                item.warna,
                item.ukuran
            )
            itemView.tv_price.text = BMoney.toRupiah(item.hargaJual.toString())
            val grandPrice = item.hargaJual * item.qty
            itemView.tv_grand_price.text = BMoney.toRupiah(grandPrice.toString())
            itemView.tv_delete.onClick { listener.onCartDelete(item, pos) }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater
            .from(p0.context).inflate(R.layout.item_cart, p0, false)
    )


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bindView(data[p1], p1)
    }

    fun addItem(dataNew: CartEntity) {
        data.add(0, dataNew)
        this.notifyItemInserted(0)
    }

    fun deleteItem(pos: Int) {
        data.removeAt(pos)
        this.notifyItemRemoved(pos)
        this.notifyItemRangeChanged(pos,data.size)
    }

    fun clear() {
        data.clear()
        this.notifyDataSetChanged()
    }

}