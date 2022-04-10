package tear.of.sun.widget

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flyco.dialog.widget.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_rate_us.*
import tear.of.sun.R
import tear.of.sun.utils.DialogBtnClickListener
import tear.of.sun.utils.ToastType
import tear.of.sun.utils.click
import tear.of.sun.utils.showToast

class DialogRate(context: Context,private val listener:DialogBtnClickListener) : BaseDialog<DialogRate>(context) {
    override fun onCreateView(): View {
        widthScale(0.85f)
        return View.inflate(context, R.layout.dialog_rate_us, null)
    }

    override fun setUiBeforShow() {
        setCanceledOnTouchOutside(false)
        yes.click {
            dismiss()
            listener.click()
        }
        no.click {
            dismiss()
        }
    }
}