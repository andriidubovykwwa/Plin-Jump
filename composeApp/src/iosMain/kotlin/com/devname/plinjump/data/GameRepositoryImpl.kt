package com.devname.plinjump.data

import com.devname.plinjump.domain.GameRepository
import com.devname.plinjump.presentation.screen.SharedData
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.getCurrentDayOfMonth
import platform.Foundation.NSUserDefaults

class GameRepositoryImpl : GameRepository {
    private val userDefaults = NSUserDefaults.standardUserDefaults()

    companion object {
        private const val COINS_KEY = "rating"
        private const val SHIELDS_KEY = "shields"
        private const val FIREBALLS_KEY = "fireballs"
        private const val HIGH_SCORE_KEY = "high_score"
        private const val SKIN_KEY = "skin"
        private const val SOUND_KEY = "sound"
        private const val MUSIC_KEY = "music"
        private const val SELECTED_SKIN_KEY = "selected_skin"
        private const val DAILY_PLAYED_GAMES_KEY = "daily_played_games"
        private const val DAILY_RECORD_KEY = "daily_record"
        private const val LAST_DAILY_RESET_DAY_KEY = "last_daily_reset_day_key"
        private const val RECEIVE_DAILY_REWARD_KEY = "daily_reward_key"
        const val NUMBER_OF_DAILY_REWARDS = 3
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
        processScoreForDailyData(value)
        if (value <= getHighScore()) return
        SharedData.updateHighScore(value)
        userDefaults.setInteger(value.toLong(), forKey = HIGH_SCORE_KEY)
    }

    override suspend fun getSkinStatuses(): List<Boolean> {
        return List(GameConfig.Skin.entries.size) { index ->
            if (index == 0) {
                true
            } else if (userDefaults.objectForKey("$SKIN_KEY$index") != null) {
                userDefaults.boolForKey("$SKIN_KEY$index")
            } else false
        }
    }

    override suspend fun activateSkin(index: Int) {
        userDefaults.setBool(true, forKey = "$SKIN_KEY$index")
    }

    override suspend fun getSelectedSkin(): Int {
        return if (userDefaults.objectForKey(SELECTED_SKIN_KEY) != null) {
            userDefaults.integerForKey(SELECTED_SKIN_KEY).toInt()
        } else 0
    }

    override suspend fun setSelectedSkin(index: Int) {
        SharedData.updateSelectedSkinIndex(index)
        userDefaults.setInteger(index.toLong(), forKey = SELECTED_SKIN_KEY)
    }

    override suspend fun getMusic(): Int {
        return if (userDefaults.objectForKey(MUSIC_KEY) != null) {
            userDefaults.integerForKey(MUSIC_KEY).toInt()
        } else 5
    }

    override suspend fun setMusic(value: Int) {
        userDefaults.setInteger(value.toLong(), forKey = MUSIC_KEY)
    }

    override suspend fun getSound(): Int {
        return if (userDefaults.objectForKey(SOUND_KEY) != null) {
            userDefaults.integerForKey(SOUND_KEY).toInt()
        } else 5
    }

    override suspend fun setSound(value: Int) {
        userDefaults.setInteger(value.toLong(), forKey = SOUND_KEY)
    }

    override suspend fun getDailyPlayedGames(): Int {
        return if (userDefaults.objectForKey(DAILY_PLAYED_GAMES_KEY) != null) {
            userDefaults.integerForKey(DAILY_PLAYED_GAMES_KEY).toInt()
        } else 0
    }

    override suspend fun getDailyRecord(): Int {
        return if (userDefaults.objectForKey(DAILY_RECORD_KEY) != null) {
            userDefaults.integerForKey(DAILY_RECORD_KEY).toInt()
        } else 0
    }

    override suspend fun setRewardReceived(index: Int) {
        userDefaults.setBool(true, forKey = "${RECEIVE_DAILY_REWARD_KEY}_$index")
    }

    override suspend fun isRewardReceived(index: Int): Boolean {
        val key = "${RECEIVE_DAILY_REWARD_KEY}_$index"
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.boolForKey(key)
        } else false
    }

    override suspend fun processDailyDataReset() {
        val currentDay = getCurrentDayOfMonth()
        val lastDaily = getLastDailyResetDay()
        if (currentDay == lastDaily) return
        userDefaults.setInteger(currentDay.toLong(), forKey = LAST_DAILY_RESET_DAY_KEY)
        userDefaults.setInteger(0, forKey = DAILY_PLAYED_GAMES_KEY)
        userDefaults.setInteger(0, forKey = DAILY_RECORD_KEY)
        repeat(NUMBER_OF_DAILY_REWARDS) {
            userDefaults.setBool(false, forKey = "${RECEIVE_DAILY_REWARD_KEY}_$it")
        }
    }

    private suspend fun processScoreForDailyData(score: Int) {
        userDefaults.setInteger(getDailyPlayedGames() + 1L, forKey = DAILY_PLAYED_GAMES_KEY)
        val prevDailyRecord = getDailyRecord()
        if (score > prevDailyRecord) {
            userDefaults.setInteger(score.toLong(), forKey = DAILY_RECORD_KEY)
        }
    }

    private fun getLastDailyResetDay(): Int {
        return if (userDefaults.objectForKey(LAST_DAILY_RESET_DAY_KEY) != null) {
            userDefaults.integerForKey(LAST_DAILY_RESET_DAY_KEY).toInt()
        } else 1
    }
}