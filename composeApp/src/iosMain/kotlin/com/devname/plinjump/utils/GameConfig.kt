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
    const val SPEED_X2_SCORE_THRESHOLD = 200f

    // Active bonuses
    const val SHIELD_PRICE = 20
    const val FIREBALL_PRICE = 50
    const val SHIELD_SECONDS = 25
    const val FIREBALL_SECONDS = 15

    // Daily quest
    const val QUEST_1_GAMES = 1
    const val QUEST_2_GAMES = 5
    const val QUEST_3_SCORE = 250
    const val QUEST_1_REWARD = 5
    const val QUEST_2_REWARD = 30
    const val QUEST_3_REWARD = 50


    enum class Skin(val res: DrawableResource, val price: Int) {
        BALL_1(Res.drawable.ball_1, 0),
        BALL_2(Res.drawable.ball_2, 100),
        BALL_3(Res.drawable.ball_3, 150),
        BALL_4(Res.drawable.ball_4, 250),
        BALL_5(Res.drawable.ball_5, 500),
    }
}