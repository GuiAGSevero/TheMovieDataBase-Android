package com.severo.marvel.framework.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.severo.core.data.DbConstants
import com.severo.marvel.framework.db.entity.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM ${DbConstants.CHARACTERS_TABLE_NAME}")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM ${DbConstants.CHARACTERS_TABLE_NAME}")
    suspend fun clearAll()
}