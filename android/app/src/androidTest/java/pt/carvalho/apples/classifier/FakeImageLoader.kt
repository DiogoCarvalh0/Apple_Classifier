package pt.carvalho.apples.classifier

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.bitmap.BitmapPool
import coil.decode.DataSource
import coil.memory.MemoryCache
import coil.request.DefaultRequestOptions
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.request.SuccessResult

class FakeImageLoader(
    private val context: Context
) : ImageLoader {

    private val resources = mapOf(
        "dn08P0z" to R.drawable.default_apple
    )

    var isDarkMode: Boolean = false

    @OptIn(ExperimentalCoilApi::class)
    private val disposable = object : Disposable {
        override val isDisposed get() = true
        override fun dispose() {
            // do nothing
        }
        override suspend fun await() {
            // do nothing
        }
    }

    override val defaults = DefaultRequestOptions()

    // Optionally, you can add a custom fake memory cache implementation.
    override val memoryCache get() = throw UnsupportedOperationException()

    override val bitmapPool = BitmapPool(0)

    override fun enqueue(request: ImageRequest): Disposable {
        // Always call onStart before onSuccess.
        request.target?.onStart(placeholder = processData("${request.data}", isDarkMode))
        request.target?.onSuccess(result = processData("${request.data}", isDarkMode))
        return disposable
    }

    override suspend fun execute(request: ImageRequest): ImageResult {
        return SuccessResult(
            drawable = processData("${request.data}", isDarkMode),
            request = request,
            metadata = ImageResult.Metadata(
                memoryCacheKey = MemoryCache.Key(""),
                isSampled = false,
                dataSource = DataSource.MEMORY_CACHE,
                isPlaceholderMemoryCacheKeyPresent = false
            )
        )
    }

    private fun processData(resource: String, isDarkMode: Boolean): Drawable {
        val default = ColorDrawable(if (isDarkMode) Color.WHITE else Color.BLACK)

        val keys = resources.keys.filter { key -> resource.contains(key) }

        return if (keys.isNotEmpty()) {
            context.getDrawable(resources[keys[0]] ?: 0) ?: default
        } else {
            default
        }
    }

    override fun shutdown() {
        // do nothing
    }

    override fun newBuilder() = ImageLoader.Builder(context)
}
