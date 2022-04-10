package tear.of.sun.page

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class FoundationPage(id:Int):AppCompatActivity(id) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start(savedInstanceState)
    }

    open fun start(savedInstanceState: Bundle?){}


}