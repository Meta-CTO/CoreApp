package com.metacto.core.ui.mediaplayers.videoPlayer

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerNotificationManager
import com.google.common.util.concurrent.ListenableFuture
import com.metacto.core.extensions.applyIf
import com.metacto.core.ui.extensions.downloadBitmap
import com.metacto.core.ui.mediaplayers.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

/**
 * A wrapper class for ExoPlayer's PlayerNotificationManager.
 * It sets up the notification shown to the user during audio playback and provides track metadata,
 * such as track title and icon image.
 * @param context The context used to create the notification.
 * @param sessionToken The session token used to build MediaController.
 * @param player The ExoPlayer instance.
 * @param notificationListener The listener for notification events.
 */
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
internal class MediaNotificationManager(
    private val context: Context,
    sessionToken: SessionToken,
    private val player: Player,
    notificationListener: PlayerNotificationManager.NotificationListener? = null
) {
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private val notificationManager: PlayerNotificationManager

    init {
        val mediaController = MediaController
            .Builder(context, sessionToken)
            .buildAsync()

        notificationManager = PlayerNotificationManager.Builder(
            context,
            abs(System.currentTimeMillis().toInt()),
            NOW_PLAYING_CHANNEL_ID
        )
            .setChannelNameResourceId(R.string.media_notification_channel)
            .setChannelDescriptionResourceId(R.string.media_notification_channel_description)
            .setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            .applyIf(notificationListener != null) {
                setNotificationListener(notificationListener!!)
            }
            .build()
            .apply {
                setPlayer(player)
                setUseRewindAction(true)
                setUseFastForwardAction(true)
                setUseRewindActionInCompactView(true)
                setUseFastForwardActionInCompactView(true)
                setUseRewindActionInCompactView(true)
                setUseFastForwardActionInCompactView(true)
            }
    }

    /**
     * Hides the notification.
     */
    fun hideNotification() {
        notificationManager.setPlayer(null)
    }

    /**
     * Shows the notification for the given player.
     * @param player The player instance for which the notification is shown.
     */
    fun showNotificationForPlayer(player: Player) {
        notificationManager.setPlayer(player)
    }

    private inner class DescriptionAdapter(
        private val controller: ListenableFuture<MediaController>
    ) : PlayerNotificationManager.MediaDescriptionAdapter {

        var currentIconUri: Uri? = null
        var currentBitmap: Bitmap? = null

        override fun createCurrentContentIntent(player: Player): PendingIntent? =
            controller.get().sessionActivity

        override fun getCurrentContentText(player: Player) =
            ""

        override fun getCurrentContentTitle(player: Player) =
            controller.get().mediaMetadata.title.toString()

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            val iconUri = controller.get().mediaMetadata.artworkUri
            return if (currentIconUri != iconUri || currentBitmap == null) {
                // Cache the bitmap for the current song so that successive calls to
                // `getCurrentLargeIcon` don't cause the bitmap to be recreated.
                currentIconUri = iconUri
                serviceScope.launch {
                    currentBitmap = iconUri?.let {
                        resolveUriAsBitmap(it)
                    }
                    currentBitmap?.let { callback.onBitmap(it) }
                }
                null
            } else {
                currentBitmap
            }
        }

        private suspend fun resolveUriAsBitmap(uri: Uri): Bitmap? {
            return withContext(Dispatchers.IO) {
                uri.toString().downloadBitmap(context)
            }
        }
    }
}

/**
 * The channel ID for the notification.
 */
private const val NOW_PLAYING_CHANNEL_ID = "media.NOW_PLAYING"
