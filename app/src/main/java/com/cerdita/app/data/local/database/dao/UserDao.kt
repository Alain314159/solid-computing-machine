package com.cerdita.app.data.local.database.dao

import androidx.room.*
import com.cerdita.app.data.local.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("UPDATE users SET status = :status, lastSeen = :lastSeen WHERE userId = :userId")
    suspend fun updateUserStatus(userId: String, status: String, lastSeen: Long = System.currentTimeMillis())
}
