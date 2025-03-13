package com.devname.plinjump.utils

import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL
import plinjump.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class, ExperimentalForeignApi::class)
object SoundManager {
    private var musicPlayer = initPlayer("files/music.mp3", numberOfLoops = -1)
    private var firePlayer = initPlayer("files/fire.mp3", numberOfLoops = -1)
    private var coinPickupPlayer = initPlayer("files/coin_pickup.mp3")
    private var gameOverPlayer = initPlayer("files/game_over.mp3")
    private var buttonClickPlayer = initPlayer("files/button_click.mp3")
    private var jumpPlayer = initPlayer("files/jump.mp3")

    private fun initPlayer(path: String, numberOfLoops: Int = 0): AVAudioPlayer? {
        try {
            val playerUrl = NSURL.URLWithString(Res.getUri(path))
            if (playerUrl != null) {
                val player = AVAudioPlayer(playerUrl, error = null)
                player.let {
                    it.numberOfLoops = numberOfLoops.toLong()
                    it.volume = 0.5f
                    it.prepareToPlay()
                }
                return player
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    fun playMusic(volume: Int) {
        if (volume == 0) {
            musicPlayer?.stop()
            return
        }
        musicPlayer?.let {
            it.volume = 0.1f * volume
        }
        musicPlayer?.play()
    }

    fun playCoinPickup(volume: Int) {
        if (volume == 0) return
        coinPickupPlayer?.let {
            it.volume = 0.1f * volume
        }
        coinPickupPlayer?.play()
    }

    fun playGameOver(volume: Int) {
        if (volume == 0) return
        gameOverPlayer?.let {
            it.volume = 0.1f * volume * 2f
        }
        gameOverPlayer?.play()
    }

    fun playButtonClick(volume: Int) {
        if (volume == 0) return
        buttonClickPlayer?.let {
            it.volume = 0.1f * volume * 1.5f
        }
        buttonClickPlayer?.play()
    }

    fun playJump(volume: Int) {
        if (volume == 0) return
        jumpPlayer?.let {
            it.volume = 0.1f * volume * 2f
        }
        jumpPlayer?.play()
    }

    fun playFire(volume: Int) {
        if (volume == 0) {
            firePlayer?.stop()
            return
        }
        firePlayer?.let {
            it.volume = 0.1f * volume * 1.5f
        }
        firePlayer?.play()
    }

}
