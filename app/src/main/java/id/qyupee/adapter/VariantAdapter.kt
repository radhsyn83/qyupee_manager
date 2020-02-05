package id.qyupee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.qyupee.R
import id.qyupee.model.product.VariantList
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

//
// Created by Fathur Radhy 
// on May 2019-05-28.
//

class VariantAdapter(
    val context: Context,
    val listener: Listener?
) : RecyclerView.Adapter<VariantAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClicked(data: VariantList.Data)
    }

    var data = arrayListOf<VariantList.Data>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(res: VariantList.Data) {
            //SKU PRODUK
            itemView.find<TextView>(R.id.tvCode).text = res.kode
            //SIZE PRODUK
            itemView.find<TextView>(R.id.tvSize).text = res.ukuran

            //ITEMCLICK
            itemView.onClick { listener?.onItemClicked(res) }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.item_variant,
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

    fun addItem(productNew: VariantList.Data) {
        data.add(productNew)
        this.notifyItemInserted(itemCount)
    }

    fun addItem(productNew: ArrayList<VariantList.Data>) {
        data.clear()
        data.addAll(productNew)
        this.notifyItemInserted(itemCount)
    }

}