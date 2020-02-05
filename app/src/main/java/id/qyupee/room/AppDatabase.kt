package id.qyupee.room

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cart(): CartDao

    companion object {

        private var INSTANCE: AppDatabase? = null

//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE tb_catatan (" +
//                        "id INTEGER primary key autoincrement NULL, " +
//                        "FIRESTORE_ID TEXT NULL," +
//                        "CAT_JUDUL TEXT NULL," +
//                        "CAT_ISI TEXT NULL," +
//                        "IS_IMAGE TEXT NULL," +
//                        "IMAGE_URL TEXT NULL," +
//                        "ALERT_SEBELUM TEXT NULL," +
//                        "IS_ALERT TEXT NULL," +
//                        "DATE_UPDATE TEXT NULL," +
//                        "DATE_ADD TEXT NULL," +
//                        "STATUS TEXT NULL)")
//            }
//        }
//
//        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE tb_catatan ADD JADWAL_ID INTEGER")
//            }
//        }

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "database_qyupee")
//                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}