package id.qyupee.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.qyupee.room.CartEntity
import id.qyupee.room.CartRepository

class CartRoomViewModel(application: Application) : AndroidViewModel(application) {
    private val cartRepo: CartRepository = CartRepository(application)
    internal var all: LiveData<List<CartEntity>>

    init {
        all = cartRepo.getAll()
    }

    fun getAll() {
        all = cartRepo.getAll()
    }

    fun insert(cartEntity: CartEntity) {
        cartRepo.insert(cartEntity)
    }

    fun update(cartEntity: CartEntity) {
        cartRepo.update(cartEntity)
    }

    fun nukeTable() {
        cartRepo.nukeTable()
    }

    fun addQty(cartEntity: CartEntity) {
        cartRepo.addQty(cartEntity)
    }

    fun minusQty(cartEntity: CartEntity) {
        cartRepo.minusQty(cartEntity)
    }

    fun delete(cartEntity: CartEntity) {
        cartRepo.delete(cartEntity)
    }
}
