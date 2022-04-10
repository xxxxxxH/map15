package tear.of.sun.page

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import tear.of.sun.R
import tear.of.sun.utils.ToastType
import tear.of.sun.utils.requestPermissions
import tear.of.sun.utils.showToast

class GuidePage : FoundationPage(R.layout.guide) {

    override fun start(savedInstanceState: Bundle?) {
        super.start(savedInstanceState)
        requestPermissions({
            startActivity(Intent(this, MapPage::class.java))
            finish()
        }, {
            showToast("no Permission app will finish", ToastType.ERROR)
            finish()
        })
    }
}