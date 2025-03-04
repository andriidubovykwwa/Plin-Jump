package com.devname.plinjump.data

import com.devname.plinjump.domain.GameRepository
import platform.Foundation.NSUserDefaults

class GameRepositoryImpl : GameRepository {
    private val userDefaults = NSUserDefaults.standardUserDefaults()

    companion object {
        private const val COINS_KEY = "rating"
        private const val SHIELDS_KEY = "shields"
        private const val FIREBALLS_KEY = "fireballs"
        private const val HIGH_SCORE_KEY = "high_score"
    }

    override suspend fun getCoins(): Int {
        return if (userDefaults.objectForKey(COINS_KEY) != null) {
            userDefaults.integerForKey(COINS_KEY).toInt()
        } else 0
    }

    override suspend fun setCoins(value: Int) {
        userDefaults.setInteger(value.toLong(), forKey = COINS_KEY)
    }

    override suspend fun getShields(): Int {
        return if (userDefaults.objectForKey(SHIELDS_KEY) != null) {
            userDefaults.integerForKey(SHIELDS_KEY).toInt()
        } else 0
    }

    override suspend fun setShields(value: Int) {
        userDefaults.setInteger(value.toLong(), forKey = SHIELDS_KEY)
    }

    override suspend fun getFireballs(): Int {
        return if (userDefaults.objectForKey(FIREBALLS_KEY) != null) {
            userDefaults.integerForKey(FIREBALLS_KEY).toInt()
        } else 0
    }

    override suspend fun setFireballs(value: Int) {
        userDefaults.setInteger(value.toLong(), forKey = FIREBALLS_KEY)
    }

    override suspend fun getHighScore(): Int {
        return if (userDefaults.objectForKey(HIGH_SCORE_KEY) != null) {
            userDefaults.integerForKey(HIGH_SCORE_KEY).toInt()
        } else 0
    }

    override suspend fun processScore(value: Int) {
        if (value <= getHighScore()) return
        userDefaults.setInteger(value.toLong(), forKey = HIGH_SCORE_KEY)
    }
}