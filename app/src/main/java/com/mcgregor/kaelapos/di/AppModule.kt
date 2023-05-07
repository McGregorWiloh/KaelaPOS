package com.mcgregor.kaelapos.di

import android.app.Application
import androidx.room.Room
import com.mcgregor.kaelapos.database.ProductDatabase
import com.mcgregor.kaelapos.repository.ProductRepository
import com.mcgregor.kaelapos.repository.ProductRepositoryImp
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
    fun provideProductDatabase(app: Application): ProductDatabase {
        return Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            "product_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductRepository(db: ProductDatabase): ProductRepository {
        return ProductRepositoryImp(db.productDao)
    }
}