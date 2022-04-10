package tear.of.sun.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hjq.permissions.XXPermissions
import com.mapbox.geojson.Point
import com.mapbox.search.ResponseInfo
import com.mapbox.search.record.FavoriteRecord
import com.mapbox.search.record.HistoryRecord
import com.mapbox.search.result.SearchResult
import com.mapbox.search.ui.view.SearchBottomSheetView
import com.mapbox.search.ui.view.category.Category
import com.studioidan.httpagent.HttpAgent
import com.studioidan.httpagent.StringCallback
import tear.of.sun.entity.DataEntity
import tear.of.sun.page.SuggesResultPage
import tear.of.sun.page.SuggestionPage
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

var mapStyle = ""

fun AppCompatActivity.requestPermissions(agree: () -> Unit, unAgree: () -> Unit) {
    XXPermissions.with(this).permission(Manifest.permission.ACCESS_FINE_LOCATION)
        .request { _, all ->
            if (all) {
                agree()
            } else {
                unAgree()
            }
        }
}

fun View.click(click: () -> Unit) {
    setOnClickListener {
        click()
    }
}

fun getData(url: String, finish: (String) -> Unit, broken: () -> Unit) {
    HttpAgent.get(url).goString(object : StringCallback() {
        override fun onDone(success: Boolean, stringResults: String?) {
            if (success) {
                stringResults?.let {
                    finish(it)
                } ?: run {
                    broken()
                }
            } else {
                broken()
            }
        }
    })
}

fun formatData(data: String):Pair<ArrayList<DataEntity>,ArrayList<String>> {
    val type = object : TypeToken<Map<String, DataEntity>>() {}.type
    val map: Map<String, DataEntity> = Gson().fromJson<Map<String, DataEntity>>(data, type)
    val list: ArrayList<DataEntity> = ArrayList<DataEntity>(map.values)
    val keys = ArrayList(map.keys)
    return Pair(list, keys)
}

fun formatImageUrl(key: String): String {
    return "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + if (TextUtils.isEmpty(key)) "LiAWseC5n46JieDt9Dkevw" else key
}

fun formatText(key :String):String{
    return if (TextUtils.isEmpty(key)) "" else key
}

fun EditText.action(search:()->Unit){
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search()
        }
        false
    }
}

fun AppCompatActivity.startSearch(key: String){
    if (TextUtils.isEmpty(key))
        return
    try {
        if (TextUtils.isEmpty(key))
            return
        val i = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(
                "http://maps.google.com/maps?q=${key}&hl=en"
            )
        )
        i.setPackage("com.google.android.apps.maps")
        startActivity(i)
    } catch (e: Exception) {
        e.printStackTrace()
        showToast("Google map not found", ToastType.ERROR)
    }
}

fun SearchBottomSheetView.init(context: Context, b: Bundle?
,category:(Category)->Unit, fav:(FavoriteRecord)->Unit,history:(HistoryRecord)->Unit,result:(SearchResult?, ResponseInfo)->Unit){
    initializeSearch(b, SearchBottomSheetView.Configuration());
    addOnCategoryClickListener{
        category(it)
    }
    addOnFavoriteClickListener{
        fav(it)
    }
    addOnHistoryClickListener{
        history(it)
    }
    addOnSearchResultClickListener{v1,v2->
        result(v1,v2)
    }
}

fun AppCompatActivity.startResultPage(it: Point) {
    val i = Intent(this, SuggesResultPage::class.java)
    i.putExtra("lat", it.latitude())
    i.putExtra("lng", it.longitude())
    startActivity(i)
}

fun AppCompatActivity.showToast(content: String, type: ToastType, duration:Long=MotionToast.SHORT_DURATION) {
    when (type) {
        ToastType.SUCCESS -> {
            MotionToast.createToast(
                this, "Success", content, MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM, duration, null
            )
        }
        ToastType.ERROR -> {
            MotionToast.createToast(
                this, "Error", content, MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM, duration, null
            )
        }
        ToastType.INFO -> {
            MotionToast.createToast(
                this, "Info", content, MotionToastStyle.INFO,
                MotionToast.GRAVITY_BOTTOM, duration, null
            )
        }
        ToastType.WARNING -> {
            MotionToast.createToast(
                this, "Waring", content, MotionToastStyle.WARNING,
                MotionToast.GRAVITY_BOTTOM, duration, null
            )
        }
    }

}

