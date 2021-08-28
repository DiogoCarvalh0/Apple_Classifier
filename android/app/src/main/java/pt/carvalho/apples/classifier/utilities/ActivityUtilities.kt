package pt.carvalho.apples.classifier.utilities

import android.app.Activity
import android.view.WindowManager

internal fun Activity.fullscreen() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}
