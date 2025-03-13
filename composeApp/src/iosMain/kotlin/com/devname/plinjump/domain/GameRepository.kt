package com.devname.plinjump.domain

interface GameRepository {
    suspend fun getCoins(): Int
    suspend fun setCoins(value: Int)
    suspend fun getShields(): Int
    suspend fun setShields(value: Int)
    suspend fun getFireballs(): Int
    suspend fun setFireballs(value: Int)
    suspend fun getHighScore(): Int
    suspend fun processScore(value: Int)
    suspend fun getSkinStatuses(): List<Boolean>
    suspend fun activateSkin(index: Int)
    suspend fun getSelectedSkin(): Int
    suspend fun setSelectedSkin(index: Int)
    suspend fun getMusic(): Int
    suspend fun setMusic(value: Int)
    suspend fun getSound(): Int
    suspend fun setSound(value: Int)
    suspend fun getDailyPlayedGames(): Int
    suspend fun getDailyRecord(): Int
    suspend fun setRewardReceived(index: Int)
    suspend fun isRewardReceived(index: Int): Boolean
    suspend fun processDailyDataReset()
}