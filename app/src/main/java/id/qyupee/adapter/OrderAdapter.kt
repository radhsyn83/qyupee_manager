package id.qyupee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.segamedev.bandros.BDate
import com.segamedev.bandros.BMoney
import id.qyupee.R
import id.qyupee.model.feed.Feed
import id.qyupee.model.order.Order
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

//
// Created by Fathur Radhy 
// on May 2019-05-28.
//



interface OrderViewHolderInterface {
    fun bind(res: Order.Data)
}

class OrderAdapter(
    val context: Context,
    val listener: Listener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface Listener {
        fun onItemClicked(data: Order.Data, itemView: View)
    }

    var data = arrayListOf<Order.Data>()

    open inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView),
        OrderViewHolderInterface {
        override fun bind(res: Order.Data) {
            itemView.find<TextView>(R.id.tv_date).text = BDate.dateFormatCustom(res.dateAdd.toString())

            itemView.find<TextView>(R.id.tv_customer).text = res.customer

            //PRICE
            itemView.find<TextView>(R.id.tv_price).text = BMoney.toRupiah(res.totalHarga.toString())

            //ITEMCLICK
            itemView.onClick { listener?.onItemClicked(res, itemView) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_order_success, parent, false)
                VH(v)
            }
            else -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_order_pending, parent, false)
                VH(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        val vh = holder as OrderViewHolderInterface
        vh.bind(data[pos])
    }

    override fun getItemViewType(position: Int): Int {
        return (data[position].status?.toInt() ?: 1)
    }

    fun addItem(productNew: ArrayList<Order.Data>) {
        data.addAll(productNew)
        this.notifyItemInserted(itemCount)
    }

    fun addItem(productNew: ArrayList<Order.Data>, clearAll: Boolean = false) {
        if (clearAll) data.clear()
        data.addAll(productNew)
        this.notifyItemInserted(itemCount)
    }

}