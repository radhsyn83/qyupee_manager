package id.qyupee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.qyupee.R
import id.qyupee.model.product.Product
import id.qyupee.utils.Tools
import kotlinx.android.synthetic.main.item_product.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

//
// Created by Fathur Radhy 
// on May 2019-05-28.
//

class ProductAdapter(
    val context: Context,
    val listener: Listener?
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClicked(data: Product.Data, itemView: View)
    }

    var data = arrayListOf<Product.Data>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(res: Product.Data) {
            //IMG
            Tools.displayImageFromUrl(itemView.image, null, res.thumbnail.toString())

            //NAMA PRODUK
            itemView.find<TextView>(R.id.tvName).text = res.nama

            //SKU PRODUK
            itemView.find<TextView>(R.id.tvCode).text = res.sku

            //ITEMCLICK
            itemView.onClick { listener?.onItemClicked(res, itemView) }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.item_product,
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

    fun addItem(productNew: ArrayList<Product.Data>) {
        data.addAll(productNew)
        this.notifyItemInserted(itemCount)
    }

    fun addItem(productNew: ArrayList<Product.Data>, clearAll: Boolean = false) {
        if (clearAll) data.clear()
        data.addAll(productNew)
        this.notifyItemInserted(itemCount)
    }

}