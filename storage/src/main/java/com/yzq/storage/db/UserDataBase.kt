package com.yzq.storage.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yzq.application.AppContext
import com.yzq.logger.LogCat


/**
 * @description 用户表数据库，Database：数据库类，用来创建数据库和表的
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

@Database(entities = [User::class], version = 3)
@TypeConverters(value = [LocalDateTimeConverter::class])
abstract class UserDataBase : RoomDatabase() {

    /**
     * 获取UserDao
     */
    abstract fun userDao(): UserDao


    companion object {
        const val TAG = "UserDataBase"

        /**
         * 数据库升级
         */
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                LogCat.i(TAG, "migrate: 1->2")
                database.execSQL("ALTER TABLE user ADD COLUMN age INTEGER default 0 NOT NULL")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                LogCat.i(TAG, "migrate: 2->3")
                // 创建临时表
                database.execSQL(
                    """CREATE TABLE IF NOT EXISTS user_new (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER NOT NULL, idCardNum TEXT DEFAULT '', phone TEXT DEFAULT '', insertTime TEXT DEFAULT (datetime('now')), updateTime TEXT DEFAULT (datetime('now')))"""
                )
                // 将数据从旧表迁移到新表
                database.execSQL("INSERT INTO user_new (id, name, age) SELECT id, name, age FROM user")
                // 删除旧表
                database.execSQL("DROP TABLE user")
                // 将新表重命名为原表名
                database.execSQL("ALTER TABLE user_new RENAME TO user")
            }
        }


        val instance: UserDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            /**
             * 创建数据库 Room.databaseBuilder()方法创建数据库 传入三个参数 第一个是上下文 第二个是数据库类 第三个是数据库名字
             */
            Room.databaseBuilder(AppContext, UserDataBase::class.java, "user_database")
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .build()
        }


    }

}