package tear.of.sun.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tear.of.sun.R
import tear.of.sun.entity.DataEntity
import tear.of.sun.utils.ItemClickListener
import tear.of.sun.utils.click
import tear.of.sun.utils.formatImageUrl
import tear.of.sun.utils.formatText

class CusAdapter(
    private val context: Context,
    private val data: ArrayList<DataEntity>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<CusAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImageView: ImageView = itemView.findViewById(R.id.itemImage)
        var itemTextView: TextView = itemView.findViewById(R.id.itemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(formatImageUrl(data[position].panoid)).into(holder.itemImageView)
        holder.itemTextView.apply {
            text = formatText(data[position].title)
            click {
                listener.itemClick(data[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}