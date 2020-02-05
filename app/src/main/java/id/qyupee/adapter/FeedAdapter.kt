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
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

//
// Created by Fathur Radhy 
// on May 2019-05-28.
//

interface FeediewHolderInterface {
    fun bind(res: Feed.Data)
}

class FeedAdapter(
    val context: Context,
    val listener: Listener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface Listener {
        fun onItemClicked(data: Feed.Data, itemView: View)
    }

    var data = arrayListOf<Feed.Data>()

    open inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView),
        FeediewHolderInterface {
        override fun bind(res: Feed.Data) {
            //DATE
            itemView.find<TextView>(R.id.tvDateTime).text = BDate.dateFormatCustom(res.dateAdd)

            //NOTE
            itemView.find<TextView>(R.id.tvNote).text = res.note

            //PRICE
            itemView.find<TextView>(R.id.tvPrice).text = BMoney.toRupiah(res.nominal)

            //ITEMCLICK
            itemView.onClick { listener?.onItemClicked(res, itemView) }
        }
    }

    inner class RestockVH(itemView: View) : VH(itemView) {
        override fun bind(res: Feed.Data) {
            super.bind(res)
            val v = res.detailVariant
            //CODE
            itemView.find<TextView>(R.id.tvCode).text = v?.kode

            //COLOR
            itemView.find<TextView>(R.id.tvColor).text = v?.nama

            //SIZE
            itemView.find<TextView>(R.id.tvSize).text = v?.ukuran

            //COUNT
            itemView.find<TextView>(R.id.tvCount).text = res.jumlahStok
        }
    }

    inner class IncomeVH(itemView: View) : VH(itemView)

    inner class OutcomeVH(itemView: View) : VH(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_feed_restock, parent, false)
                RestockVH(v)
            }
            2 -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_feed_income, parent, false)
                IncomeVH(v)
            }
            else -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_feed_outcome, parent, false)
                OutcomeVH(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        val vh = holder as FeediewHolderInterface
        vh.bind(data[pos])
    }

    override fun getItemViewType(position: Int): Int {
        return (data[position].jenis)
    }

    fun addItem(productNew: ArrayList<Feed.Data>) {
        data.addAll(productNew)
        this.notifyItemInserted(itemCount)
    }

    fun addItem(productNew: ArrayList<Feed.Data>, clearAll: Boolean = false) {
        if (clearAll) data.clear()
        data.addAll(productNew)
        this.notifyItemInserted(itemCount)
    }

}