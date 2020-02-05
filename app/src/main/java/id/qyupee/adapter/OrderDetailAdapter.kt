package id.qyupee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.segamedev.bandros.BMoney
import id.qyupee.R
import id.qyupee.model.order.OrderDetail
import org.jetbrains.anko.find
import java.util.*

//
// Created by Fathur Radhy 
// on May 2019-05-28.
//

class OrderDetailAdapter(
    val context: Context,
    val listener: Listener?
) : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClicked(data: OrderDetail.Data)
    }

    var data = arrayListOf<OrderDetail.Data>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(res: OrderDetail.Data) {
            itemView.find<TextView>(R.id.tvColor).text = res.warna
            itemView.find<TextView>(R.id.tvSize).text = res.ukuran
            itemView.find<TextView>(R.id.tvCode).text = res.kode
            itemView.find<TextView>(R.id.tvSatuan).text = "@${BMoney.toRupiah(res.hargaJual.toString())}"
            itemView.find<TextView>(R.id.tvPrice).text = BMoney.toRupiah(res.totalHargaJual.toString())
            itemView.find<TextView>(R.id.tvCount).text = res.jumlah
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.item_order_detail,
                p0,
                false
            )
        )

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(data[pos])
    }

    fun addItem(productNew: OrderDetail.Data) {
        data.add(productNew)
        this.notifyItemInserted(itemCount)
    }

    fun addItem(productNew: ArrayList<OrderDetail.Data>) {
        data.clear()
        data.addAll(productNew)
        this.notifyItemInserted(itemCount)
    }

}