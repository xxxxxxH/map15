package tear.of.sun.page

import android.os.Bundle
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.android.synthetic.main.activity_main.*
import tear.of.sun.R
import tear.of.sun.utils.*
import tear.of.sun.widget.OptionDialog

class MapPage : FoundationPage(R.layout.activity_main) {

    private val positionChangedListener by lazy {
        mapView.getIndicatorListener()
    }

    private val onMoveListener = getMoveListener()

    override fun start(savedInstanceState: Bundle?) {
        super.start(savedInstanceState)
        initMapView()
        initAction()
    }

    private fun initAction() {
        action.click {
            val optionDialog = OptionDialog()
            optionDialog.setParentFab(action)
            optionDialog.show(supportFragmentManager, optionDialog.tag)
        }
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
    }

    override fun onResume() {
        super.onResume()
        if (mapStyle != "") {
            when (mapStyle) {
                "outdoor" -> {
                    mapView.initMapUi(Style.OUTDOORS)
                }
                "sate" -> {
                    mapView.initMapUi(Style.SATELLITE)
                }
                "light" -> {
                    mapView.initMapUi(Style.LIGHT)
                }
                "tra" -> {
                    mapView.initMapUi(Style.TRAFFIC_DAY)
                }
            }
        }
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