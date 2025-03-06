package com.devname.plinjump.utils

import org.jetbrains.compose.resources.DrawableResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.ball_1
import plinjump.composeapp.generated.resources.ball_2
import plinjump.composeapp.generated.resources.ball_3
import plinjump.composeapp.generated.resources.ball_4
import plinjump.composeapp.generated.resources.ball_5

object GameConfig {
    // % of screen height
    const val BLOCK_SIZE = 0.16f
    const val JUMP_HEIGHT = 0.55f
    const val JUMP_SPEED = 0.08f
    const val DEFAULT_OBSTACLE_MOVE_SPEED = 0.06f
    const val SCORE_INCREASE_SPEED = 0.3f
    const val SPEED_X2_SCORE_THRESHOLD = 250f

    // Active bonuses
    const val SHIELD_PRICE = 15 // TODO 15
    const val FIREBALL_PRICE = 1 // TODO 40
    const val SHIELD_SECONDS = 20
    const val FIREBALL_SECONDS = 10

    enum class Skin(val res: DrawableResource, val price: Int) {
        BALL_1(Res.drawable.ball_1, 0),
        BALL_2(Res.drawable.ball_2, 50),
        BALL_3(Res.drawable.ball_3, 50),
        BALL_4(Res.drawable.ball_4, 50),
        BALL_5(Res.drawable.ball_5, 50),
    }
}