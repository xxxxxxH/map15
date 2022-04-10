package tear.of.sun.page

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.details.*
import tear.of.sun.R
import tear.of.sun.adapter.CusAdapter
import tear.of.sun.entity.DataEntity
import tear.of.sun.utils.*

class DetailsPage : FoundationPage(R.layout.details),ItemClickListener {

    lateinit var list: ArrayList<DataEntity>
    lateinit var keys: ArrayList<String>

    private val reqUrl by lazy { intent.getStringExtra("reqUrl") }

    private val imageUrl by lazy { intent.getStringExtra("imageUrl") }

    override fun start(savedInstanceState: Bundle?) {
        super.start(savedInstanceState)
        Glide.with(this).load(imageUrl).into(detailsImage)
        getData(reqUrl!!, {
            val (list, keys) = formatData(it)
            this.list = list
            this.keys = keys
            val adapter = CusAdapter(this, list, this)
            recycler.layoutManager = GridLayoutManager(this, 2)
            recycler.adapter = adapter
        }, {
            showToast("No data", ToastType.INFO)
        })
    }

    override fun itemClick(item: DataEntity, position: Int) {
        Glide.with(this).load(formatImageUrl(item.panoid)).into(detailsImage)
    }
}