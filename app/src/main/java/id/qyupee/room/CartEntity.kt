package id.qyupee.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created by Fathur Radhy 
// on July 2019-07-02.
//
@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "id_variant") var idVariant: String,
    @ColumnInfo(name = "harga_jual") var hargaJual: Int,
    @ColumnInfo(name = "qty") var qty: Int,
    @ColumnInfo(name = "status") var status: Int,
    @ColumnInfo(name = "sku") var sku: String,
    @ColumnInfo(name = "warna") var warna: String,
    @ColumnInfo(name = "ukuran") var ukuran: String

) {
    constructor() : this(null, "", 0, 0, 0, "", "", "")

    override fun toString(): String {
        return "CartEntity(id=$id, idVariant='$idVariant', hargaJual=$hargaJual, qty=$qty, status=$status, sku='$sku', warna='$warna', ukuran='$ukuran')"
    }

}