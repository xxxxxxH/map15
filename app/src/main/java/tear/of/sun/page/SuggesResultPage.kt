package tear.of.sun.page

import android.os.Bundle
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.android.synthetic.main.result.*
import tear.of.sun.R
import tear.of.sun.utils.*

class SuggesResultPage : FoundationPage(R.layout.result) {
    private val lat by lazy {
        intent.getDoubleExtra("lat", 0.0)
    }

    private val lng by lazy {
        intent.getDoubleExtra("lng", 0.0)
    }
    private val positionChangedListener by lazy {
        mapView.getIndicatorListener()
    }

    private val onMoveListener = getMoveListener()
    override fun start(savedInstanceState: Bundle?) {
        super.start(savedInstanceState)
        initMapView()
    }

    private fun initMapView() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            mapView.initLocationComponent(positionChangedListener)
            mapView.addMoveListener(onMoveListener)
        }
        mapView.addMarker(Point.fromLngLat(lng, lat))
        mapView.fly(Point.fromLngLat(lng, lat))
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.removeListener({
            mapView.location
                .removeOnIndicatorPositionChangedListener(positionChangedListener)
        }, {
            mapView.gestures.removeOnMoveListener(onMoveListener)
        })
    }
}