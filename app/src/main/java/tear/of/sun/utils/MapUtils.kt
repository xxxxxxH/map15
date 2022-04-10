@file:JvmName("MapUtils")

package tear.of.sun.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.android.synthetic.main.activity_main.*
import tear.of.sun.R

fun MapView.myLocation() {
    val listener = object : OnIndicatorPositionChangedListener {
        override fun onIndicatorPositionChanged(point: Point) {
            setTag(R.id.appViewMyLocationId, point)
            getMapboxMap().setCamera(CameraOptions.Builder().center(point).build())
            gestures.focalPoint = getMapboxMap().pixelForCoordinate(point)
            location.removeOnIndicatorPositionChangedListener(this)
        }
    }
    location.addOnIndicatorPositionChangedListener(listener)
}

fun MapView.cameraClick(block: (Double, Double) -> Unit) {
    getMapboxMap().addOnMapClickListener {
        block(it.longitude(), it.latitude())
        true
    }
}

fun MapView.fly(p: Point) {
    getMapboxMap().flyTo(cameraOptions {
        center(p)
        zoom(14.0)
    })
}

fun MapView.initMapUi(style: String) {
    getMapboxMap().apply {
        setCamera(cameraOptions {
            zoom(14.0)
        })
        loadStyleUri(style) {
            addMyLocationPoint()
        }
    }
}

fun MapView.addMyLocationPoint() {
    location.updateSettings {
        enabled = true
        pulsingEnabled = true
    }
}

fun MapView.cameraChange(block: (Double, Double) -> Unit) {
    getMapboxMap().addOnCameraChangeListener {
        getMapboxMap().cameraState.center.let {
            block(it.longitude(), it.latitude())
        }
    }
}


fun MapView.setCamera(center: Point) {
    getMapboxMap().setCamera(
        cameraOptions {
            center(center)
            zoom(14.0)
        }
    )
}

fun MapView.addMarker(p: Point) {
    val bitmap: Bitmap = BitmapFactory.decodeResource(this.context.resources, R.mipmap.red_marker)
    annotations.cleanup()
    val markerManager = annotations.createPointAnnotationManager(AnnotationConfig())
    val pointAnnotationOptions = PointAnnotationOptions()
        .withPoint(p)
        .withIconImage(bitmap)
    markerManager.create(pointAnnotationOptions)
}

fun getMoveListener(): OnMoveListener {
    return object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }
}

fun MapView.getIndicatorListener():OnIndicatorPositionChangedListener{
    return OnIndicatorPositionChangedListener {
        this.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        this.gestures.focalPoint = this.getMapboxMap().pixelForCoordinate(it)
    }
}

fun MapView.addMoveListener(listener: OnMoveListener){
    this.gestures.addOnMoveListener(listener)
}

fun MapView.initLocationComponent(listener: OnIndicatorPositionChangedListener){
    val plugin = location
    plugin.updateSettings {
        this.enabled = true
    }
    plugin.addOnIndicatorPositionChangedListener(
        listener
    )
}

fun MapView.removeListener(listener1: ()-> Unit,listener2: ()-> Unit){
    listener1()
    listener2()
}