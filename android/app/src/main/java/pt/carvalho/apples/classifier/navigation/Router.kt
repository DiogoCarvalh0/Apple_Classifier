package pt.carvalho.apples.classifier.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

internal interface Router {
    fun navigateToSystemPreferences()
}

internal class RouterImpl(
    private val context: Context,
    private val name: String
) : Router {
    override fun navigateToSystemPreferences() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).also { intent ->
            intent.data = Uri.fromParts("package", name, null)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)
    }
}
