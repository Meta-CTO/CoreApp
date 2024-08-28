package com.metacto.core.presentation.components.videoPlayer

import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Rational
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.metacto.coreApp.R
import org.koin.android.ext.android.inject

internal class VideoPlayerActivity : AppCompatActivity() {
    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set content view
        setContentView(R.layout.activity_video_player)

        // Setup views
        setupViews()

        // Config player view
        intent?.getStringExtra(KEY_PLAYER_ID)?.let {
            configPlayerView(it)
        }
    }

    private fun setupViews() {
        val ibPip = findViewById<ImageButton>(R.id.ib_pip)
        ibPip.setOnClickListener {
            enablePip()
        }
    }

    private fun enablePip() {
        val params = PictureInPictureParams.Builder().run {
            setAspectRatio(Rational(16, 9))
            build()
        }

        if (isInPictureInPictureMode.not()) {
            enterPictureInPictureMode(params)
        }
    }

    private fun configPlayerView(playerId: String) {
        val playerManagers by inject<MutableMap<String, VideoPlayerManager>>()
        exoPlayer = playerManagers[playerId]?.exoPlayer ?: return

        val playerView = findViewById<PlayerView>(R.id.player_view)
        playerView.player = exoPlayer
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getStringExtra(KEY_PLAYER_ID)?.let {
            configPlayerView(it)
        }
    }

    override fun onStop() {
        exoPlayer?.pause()
        super.onStop()
    }

    companion object {
        private const val KEY_PLAYER_ID = "player_id"

        fun start(context: Context, playerId: String) {
            val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                putExtra(KEY_PLAYER_ID, playerId)
            }
            context.startActivity(intent)
        }
    }
}