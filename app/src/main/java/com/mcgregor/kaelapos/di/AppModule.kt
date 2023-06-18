package com.mcgregor.kaelapos.di

import android.app.Application
import androidx.room.Room
import com.mcgregor.kaelapos.database.KaelaPOSDatabase
import com.mcgregor.kaelapos.repository.KaelaPOSRepository
import com.mcgregor.kaelapos.repository.KaelaPOSRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKaelaPOSDatabase(app: Application): KaelaPOSDatabase {
        return Room.databaseBuilder(
            app,
            KaelaPOSDatabase::class.java,
            "kaelapos_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideKaelaPOSRepository(db: KaelaPOSDatabase): KaelaPOSRepository {
        return KaelaPOSRepositoryImp(db.productDao)
    }
}