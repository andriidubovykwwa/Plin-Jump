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
}