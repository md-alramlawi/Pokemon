package database

expect class DataBaseFactory {
    fun createRoomDatabase(): AppDatabase
}
