package tear.of.sun.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import tear.of.sun.R
import tear.of.sun.utils.ToastType
import tear.of.sun.utils.click
import tear.of.sun.utils.mapStyle
import tear.of.sun.utils.showToast

class MapStyleContainer : LinearLayout {
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context): View {
        val v: View = LayoutInflater.from(context).inflate( R.layout.style, this, true)
        v.findViewById<TextView>(R.id.outdoor).click {
            mapStyle = "outdoor"
            (context as AppCompatActivity).showToast("select $mapStyle", ToastType.SUCCESS)
        }
        v.findViewById<TextView>(R.id.sate).click {
            mapStyle = "sate"
            (context as AppCompatActivity).showToast("select $mapStyle", ToastType.SUCCESS)
        }
        v.findViewById<TextView>(R.id.light).click {
            mapStyle = "light"
            (context as AppCompatActivity).showToast("select $mapStyle", ToastType.SUCCESS)
        }
        v.findViewById<TextView>(R.id.tra).click {
            mapStyle = "tra"
            (context as AppCompatActivity).showToast("select $mapStyle", ToastType.SUCCESS)
        }
        return v
    }
}