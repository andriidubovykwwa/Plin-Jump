package com.devname.plinjump.utils

import org.jetbrains.skiko.OS
import org.jetbrains.skiko.OSVersion
import org.jetbrains.skiko.available
import platform.Foundation.setValue
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIInterfaceOrientation
import platform.UIKit.UIInterfaceOrientationLandscapeRight
import platform.UIKit.UIInterfaceOrientationMaskAllButUpsideDown
import platform.UIKit.UIInterfaceOrientationMaskLandscapeRight
import platform.UIKit.UIInterfaceOrientationUnknown
import platform.UIKit.setNeedsUpdateOfSupportedInterfaceOrientations

class OrientationManager {
    private val shouldUseNewOrientationAPI = available(OS.Ios to OSVersion(16))
    private var _orientation = Orientation.ALL
    var orientation: Orientation
        get() = _orientation
        set(value) {
            _orientation = value
            appOrientation = value.mask
            if (shouldUseNewOrientationAPI) {
                UIApplication.sharedApplication.keyWindow
                    ?.rootViewController
                    ?.setNeedsUpdateOfSupportedInterfaceOrientations()
            } else {
                UIDevice.currentDevice.setValue(value.interfaceOrientation, forKey = "orientation")
            }
        }


    enum class Orientation(
        val mask: ULong,
        val interfaceOrientation: UIInterfaceOrientation,
    ) {
        ALL(
            mask = UIInterfaceOrientationMaskAllButUpsideDown,
            interfaceOrientation = UIInterfaceOrientationUnknown,
        ),
        LANDSCAPE(
            mask = UIInterfaceOrientationMaskLandscapeRight,
            interfaceOrientation = UIInterfaceOrientationLandscapeRight,
        ),
    }

    companion object {
        var appOrientation = UIInterfaceOrientationMaskAllButUpsideDown
    }
}