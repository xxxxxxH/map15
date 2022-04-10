package tear.of.sun.page

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.setting.*
import tear.of.sun.R
import tear.of.sun.utils.DialogBtnClickListener
import tear.of.sun.utils.ToastType
import tear.of.sun.utils.click
import tear.of.sun.utils.showToast
import tear.of.sun.widget.DialogRate

class SettingPage : FoundationPage(R.layout.setting) , DialogBtnClickListener{

    private val dialog by lazy {
        DialogRate(this, this)
    }

    override fun start(savedInstanceState: Bundle?) {
        super.start(savedInstanceState)
        rate.click { dialog.show() }
        share.click {
            try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_SUBJECT, "Music Player")
                val aux: String = resources.getString(R.string.app_name) + "https://play.google.com/store/apps/details?id=" + packageName
                i.putExtra(Intent.EXTRA_TEXT, aux)
                startActivity(Intent.createChooser(i, "choose one"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        update.click {
            showToast("no new version", ToastType.INFO)
        }
    }

    override fun click() {
        showToast("Thank you", ToastType.SUCCESS)
    }
}