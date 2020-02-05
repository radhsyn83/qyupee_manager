package id.qyupee.net

import id.qyupee.model.DefaultModel
import id.qyupee.model.auth.Auth
import id.qyupee.model.cart.Cart
import id.qyupee.model.feed.Feed
import id.qyupee.model.order.Order
import id.qyupee.model.order.OrderDetail
import id.qyupee.model.product.Product
import id.qyupee.model.product.Variant
import id.qyupee.model.product.VariantList
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


//
// Created by Fathur Radhy
// on May 2019-05-27.
//
interface ApiServices {
    @POST("member/feed/list")
    fun feed(): Call<Feed>

    @POST("member/product/list")
    fun product(): Call<Product>

    @POST("member/cart/detail")
    @FormUrlEncoded
    fun cart(
        @Field("id_variant") idVariants: String
    ): Call<Cart>

    @POST("member/product/variant")
    @FormUrlEncoded
    fun variant(
        @Field("id_product") idProduct: String
    ): Call<Variant>

    @POST("member/feed/add")
    @FormUrlEncoded
    fun addFeed(
        @Field("jenis") jenis: String,
        @Field("note") note: String,
        @Field("nominal") nominal: String,
        @Field("jml_stok") jml_stok: String,
        @Field("id_variant") id_variant: String
    ): Call<DefaultModel>

    @POST("member/order/transaction")
    @FormUrlEncoded
    fun checkout(
        @Field("id_admin") id_admin: String,
        @Field("customer") customer: String,
        @Field("status") status: String,
        @Field("id_variant") id_variant: String,
        @Field("harga_jual") harga_jual: String,
        @Field("qty") qty: String
    ): Call<DefaultModel>

    @POST("member/login")
    @FormUrlEncoded
    fun auth(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Call<Auth>

    @POST("member/order/list")
    fun order(): Call<Order>

    @POST("member/order/detail")
    @FormUrlEncoded
    fun order(
        @Field("id_order") id_order: String
    ): Call<OrderDetail>

    @POST("member/product/variant_list")
    fun variant(): Call<VariantList>
}
