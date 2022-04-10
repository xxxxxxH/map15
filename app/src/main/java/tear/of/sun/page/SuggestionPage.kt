package tear.of.sun.page

import android.os.Bundle
import com.mapbox.search.MapboxSearchSdk.getSearchEngine
import com.mapbox.search.ResponseInfo
import com.mapbox.search.SearchMultipleSelectionCallback
import com.mapbox.search.SearchOptions
import com.mapbox.search.SearchSelectionCallback
import com.mapbox.search.result.SearchResult
import com.mapbox.search.result.SearchSuggestion
import kotlinx.android.synthetic.main.suggestion.*
import tear.of.sun.R
import tear.of.sun.utils.*

class SuggestionPage : FoundationPage(R.layout.suggestion), SearchSelectionCallback,
    SearchMultipleSelectionCallback{
    var searchEngine = getSearchEngine()
    override fun start(savedInstanceState: Bundle?) {
        super.start(savedInstanceState)
        editText.action{
            startSearch(editText.text.toString())
        }
        searchSheet.init(this, savedInstanceState,{
            showToast("Please wait", ToastType.INFO)
            searchEngine.search(it.geocodingCanonicalName, SearchOptions(), this)
        },{
            showToast("Please wait", ToastType.INFO)
            startResultPage(it.coordinate)
        },{
            showToast("Please wait", ToastType.INFO)
            it.coordinate?.let {p->
                startResultPage(p)
            }?: kotlin.run {
                searchEngine.search(it.name, SearchOptions(), this)
            }
        },{searchResult, responseInfo ->
            showToast("Please wait", ToastType.INFO)
            searchResult?.let {
                startResultPage(it.coordinate!!)
            }?: kotlin.run {
                searchEngine.search(searchResult!!.name, SearchOptions(), this)
            }
        })
    }

    override fun onCategoryResult(
        suggestion: SearchSuggestion,
        results: List<SearchResult>,
        responseInfo: ResponseInfo
    ) {

    }

    override fun onError(e: Exception) {
        showToast("Error", ToastType.ERROR)
    }

    override fun onResult(
        suggestion: SearchSuggestion,
        result: SearchResult,
        responseInfo: ResponseInfo
    ) {

    }

    override fun onSuggestions(suggestions: List<SearchSuggestion>, responseInfo: ResponseInfo) {
        if (suggestions.isNotEmpty()){
            searchEngine.select(suggestions, this)
        }else{
            showToast("No suggestions", ToastType.ERROR)
        }
    }

    override fun onResult(
        suggestions: List<SearchSuggestion>,
        results: List<SearchResult>,
        responseInfo: ResponseInfo
    ) {
        if (results.isNotEmpty()) {
            if (results[0].coordinate != null) {
               startResultPage(results[0].coordinate!!)
            } else {
                showToast("No suggestions", ToastType.ERROR)
            }
        }
    }
}