package tear.of.sun.widget

import android.app.Dialog
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import tear.of.sun.R
import tear.of.sun.page.InteractivePage
import tear.of.sun.page.SettingPage
import tear.of.sun.page.SuggestionPage
import tear.of.sun.utils.click


class OptionDialog : AAH_FabulousFragment() {
    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView: View = View.inflate(context, R.layout.option, null)
        val content = contentView.findViewById<LinearLayout>(R.id.content)
        contentView.findViewById<TextView>(R.id.suggestion)
            .click {
                context?.startActivity(Intent(context, SuggestionPage::class.java))
                dismiss()
            }
        contentView.findViewById<TextView>(R.id.inter)
            .click {
                context?.startActivity(Intent(context, InteractivePage::class.java))
                dismiss()
            }
        contentView.findViewById<TextView>(R.id.setting)
            .click {
                context?.startActivity(Intent(context, SettingPage::class.java))
                dismiss()
            }
        contentView.findViewById<TextView>(R.id.close).click {
            closeFilter("")
        }
        setViewMain(content)
        setMainContentView(contentView)
        super.setupDialog(dialog, style)
    }
}