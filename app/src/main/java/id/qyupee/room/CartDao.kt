package id.qyupee.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CartDao {
    @Insert
    fun insert(cartEntity: CartEntity)

    @Query("DELETE FROM cart")
    fun nukeTable()

    @Update
    fun update(cart: CartEntity)

    @Delete
    fun delete(cart: CartEntity)

    @Query("SELECT * FROM cart ORDER BY id ASC" )
    fun getAll() : LiveData<List<CartEntity>>

    @Query("UPDATE cart SET qty = qty+1 WHERE id_variant = :idVariant ")
    fun addQty(idVariant: String)

    @Query("UPDATE cart SET qty = qty-1 WHERE id_variant = :idVariant ")
    fun minusQty(idVariant: String)
}