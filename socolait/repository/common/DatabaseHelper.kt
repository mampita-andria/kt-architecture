package mg.socolait.repository.common

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import mg.socolait.common.exceptions.TechnicalException
import mg.socolait.data.domainobject.*


class DatabaseHelper(context: Context) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(database: SQLiteDatabase, connectionSource: ConnectionSource) {

        try {
            TableUtils.createTable(connectionSource, User::class.java)
            TableUtils.createTable(connectionSource, Customer::class.java)
            TableUtils.createTable(connectionSource, Photo::class.java)
            TableUtils.createTable(connectionSource, Product::class.java)
            TableUtils.createTable(connectionSource, Order::class.java)
            TableUtils.createTable(connectionSource, Sale::class.java)
            TableUtils.createTable(connectionSource, Stock::class.java)
            TableUtils.createTable(connectionSource, OrderProduct::class.java)
            TableUtils.createTable(connectionSource, SaleProduct::class.java)
        } catch (e: android.database.SQLException) {
            e.printStackTrace()
            throw TechnicalException(e)
        } catch (e: java.sql.SQLException) {
            e.printStackTrace()
            throw TechnicalException(e)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase, connectionSource: ConnectionSource, oldVersion: Int, newVersion: Int) {
        try {
            TableUtils.dropTable<OrderProduct, Long>(connectionSource, OrderProduct::class.java, false)
            TableUtils.dropTable<SaleProduct, Long>(connectionSource, SaleProduct::class.java, false)
            TableUtils.dropTable<Product, Long>(connectionSource, Product::class.java, false)
            TableUtils.dropTable<Stock, Long>(connectionSource, Stock::class.java, false)
            TableUtils.dropTable<Sale, Long>(connectionSource, Sale::class.java, false)
            TableUtils.dropTable<Order, Long>(connectionSource, Order::class.java, false)
            TableUtils.dropTable<Photo, Long>(connectionSource, Photo::class.java, false)
            TableUtils.dropTable<Customer, Long>(connectionSource, Customer::class.java, false)
            TableUtils.dropTable<User, Long>(connectionSource, User::class.java, false)
            onCreate(db)
        } catch (e: Exception) {
            Log.e(TAG, "exception during onUpgrade", e)
            throw TechnicalException(e)
        }

    }

    companion object {

        private val TAG = DatabaseHelper::class.java.simpleName
        // name of the database file for your application -- change to something appropriate for your app
        val DATABASE_NAME = "socolait.sqlite"

        // any time you make changes to your database objects, you may have to increase the database version
        private val DATABASE_VERSION = 1
    }
}
