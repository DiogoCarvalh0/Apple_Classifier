package pt.carvalho.apples.classifier.utilities

import androidx.test.platform.app.InstrumentationRegistry
import java.io.InputStream

internal fun assets(name: String): InputStream {
    return InstrumentationRegistry.getInstrumentation()
        .context.resources.assets.open(name)
}
