package tear.of.sun.page

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.inter.*
import tear.of.sun.R
import tear.of.sun.adapter.CusAdapter
import tear.of.sun.entity.DataEntity
import tear.of.sun.utils.*

class InteractivePage:FoundationPage(R.layout.inter) , ItemClickListener{

    lateinit var list:ArrayList<DataEntity>
    lateinit var keys:ArrayList<String>

    override fun start(savedInstanceState: Bundle?) {
        super.start(savedInstanceState)
        getData("https://www.google.com/streetview/feed/gallery/data.json", {
            val (list, keys) = formatData(it)
            this.list = list
            this.keys = keys
            val adapter = CusAdapter(this, list, this)
            recycler.layoutManager = GridLayoutManager(this, 2)
            recycler.adapter = adapter
        }, {
            showToast("No data", ToastType.WARNING)
        })
    }

    override fun itemClick(item: DataEntity, position: Int) {
        val reqUrl = "https://www.google.com/streetview/feed/gallery/collection/" + keys[position] + ".json"
        val imageUrl = formatImageUrl(item.panoid)
        startActivity(Intent(this, DetailsPage::class.java).apply {
            putExtra("reqUrl", reqUrl)
            putExtra("imageUrl", imageUrl)
        })
    }
}