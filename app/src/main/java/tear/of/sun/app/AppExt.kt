package tear.of.sun.app

import android.app.Application
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.search.MapboxSearchSdk
import tear.of.sun.R

class AppExt:Application() {

    override fun onCreate() {
        super.onCreate()
            MapboxSearchSdk.initialize(
            this,
            resources.getString(R.string.mapbox_access_token),
            LocationEngineProvider.getBestLocationEngine(this)
        )
    }
}