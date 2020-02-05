package id.qyupee.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class CartRepository(application: Application) {
    private val cartDao: CartDao
    private val listLiveData: LiveData<List<CartEntity>>

    init {
        val appDb = AppDatabase.getDatabase(application)
        cartDao = appDb.cart()
        listLiveData = cartDao.getAll()
    }

    fun getAll(): LiveData<List<CartEntity>> {
        return listLiveData
    }

    fun insert(cart: CartEntity) {
        InsertAsyncTask(cartDao).execute(cart)
    }

    fun update(cart: CartEntity) {
        UpdateAsyncTask(cartDao).execute(cart)
    }

    fun addQty(cart: CartEntity) {
        AddQtyAsyncTask(cartDao).execute(cart)
    }

    fun minusQty(cart: CartEntity) {
        MinusQtyAsyncTask(cartDao).execute(cart)
    }

    fun nukeTable() {
        DeleteAllAsyncTask(cartDao).execute()
    }

    fun delete(cart: CartEntity) {
        DeleteAsyncTask(cartDao).execute(cart)
    }

    private class InsertAsyncTask (private val mAsyncTaskDao: CartDao) : AsyncTask<CartEntity, Void, Void>() {
        override fun doInBackground(vararg params: CartEntity): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class UpdateAsyncTask (private val mAsyncTaskDao: CartDao) : AsyncTask<CartEntity, Void, Void>() {
        override fun doInBackground(vararg params: CartEntity): Void? {
            mAsyncTaskDao.update(params[0])
            return null
        }
    }

    private class AddQtyAsyncTask (private val mAsyncTaskDao: CartDao) : AsyncTask<CartEntity, Void, Void>() {
        override fun doInBackground(vararg params: CartEntity): Void? {
            mAsyncTaskDao.addQty(params[0].idVariant)
            return null
        }
    }

    private class MinusQtyAsyncTask (private val mAsyncTaskDao: CartDao) : AsyncTask<CartEntity, Void, Void>() {
        override fun doInBackground(vararg params: CartEntity): Void? {
            mAsyncTaskDao.minusQty(params[0].idVariant)
            return null
        }
    }

    private class DeleteAsyncTask (private val mAsyncTaskDao: CartDao) : AsyncTask<CartEntity, Void, Void>() {
        override fun doInBackground(vararg params: CartEntity): Void? {
            mAsyncTaskDao.delete(params[0])
            mAsyncTaskDao.getAll()
            return null
        }
    }

    private class DeleteAllAsyncTask (private val mAsyncTaskDao: CartDao) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void? {
            mAsyncTaskDao.nukeTable()
            return null
        }
    }

}
