package com.iak.ali.mycrud;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Ali on 1/27/2018.
 */

public interface ApiInterface {
    @GET("produk.php")
    Call<List<Produk>> getProduk();


    @GET("produkUser.php?")
    Call<List<Produk>> getProdukUser(@Query("id_user") String id_user);

    @GET("user.php")
    Call<List<User>> getUser(@Query("email") String email);


    @FormUrlEncoded
    @POST("addUser.php")
    Call<Value> addUser (@Field("nama") String nama,
                         @Field("email") String email,
                         @Field("no_tlp") String no_tlp,
                         @Field("alamat") String alamat,
                         @Field("photo") String photo);

    @FormUrlEncoded
    @POST("update.php")
    Call<Value> addProduk(@Field("nm_produk") String nm_produk,
                          @Field("hrg_produk") String hrg_produk,
                          @Field("stok") String stok,
                          @Field("deskripsi") String deskripsi);
    @Multipart
    @POST("updateProduk.php")
    Call<ResponseBody> upload (@Part MultipartBody.Part file,
                               @Part("nm_produk") RequestBody nm_produk,
                               @Part("hrg_produk") RequestBody hrg_produk,
                               @Part("stok") RequestBody stok,
                               @Part("deskripsi") RequestBody deskripsi,
                               @Part("id_user") RequestBody id_user);

    @Multipart
    @POST("editProduk.php")
    Call<ResponseBody> edit (@Part MultipartBody.Part file,
                               @Part("nm_produk") RequestBody nm_produk,
                               @Part("hrg_produk") RequestBody hrg_produk,
                               @Part("stok") RequestBody stok,
                               @Part("id") RequestBody id,
                               @Part("deskripsi") RequestBody deskripsi,
                               @Part("id_user") RequestBody id_user);
    @FormUrlEncoded
    @POST("editProduk.php")
    Call<Value> editWithoutImage (@Field("nm_produk") String nm_produk,
                             @Field("hrg_produk") String hrg_produk,
                             @Field("stok") String stok,
                             @Field("id") String id,
                             @Field("deskripsi") String deskripsi,
                             @Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("deleted.php")
    Call<Value> hapus(@Field("id") String id);
}
