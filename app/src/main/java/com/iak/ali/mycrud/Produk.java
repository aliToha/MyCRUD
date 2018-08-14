package com.iak.ali.mycrud;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ali on 1/27/2018.
 */

public class Produk implements Parcelable {
    @SerializedName("id")
    String ID;
    @SerializedName("nm_produk")
    String namaProduk;
    @SerializedName("hrg_produk")
    String hargaProduk;
    @SerializedName("stok")
    String stokProduk;
    @SerializedName("gmbr_produk")
    String imgProduk;
    @SerializedName("deskripsi")
    String deskripsi;
    @SerializedName("id_user")
    String id_user;
    @SerializedName("nama")
    String nama;
    @SerializedName("no_tlp")
    String no_tlp;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("photo")
    String photo;

    public String getID() {
        return ID;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public String getStokProduk() {
        return stokProduk;
    }

    public String getImgProduk() {
        return imgProduk;
    }
    public String getDeskripsi() {
        return deskripsi;
    }
    public String getId_user() {
        return id_user;
    }
    public String getNama() {
        return nama;
    }
    public String getNo_tlp() {
        return no_tlp;
    }
    public String getAlamat() {
        return alamat;
    }
    public String getPhoto() {
        return photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.namaProduk);
        dest.writeString(this.hargaProduk);
        dest.writeString(this.stokProduk);
        dest.writeString(this.imgProduk);
        dest.writeString(this.deskripsi);
        dest.writeString(this.id_user);
        dest.writeString(this.nama);
        dest.writeString(this.no_tlp);
        dest.writeString(this.alamat);
        dest.writeString(this.photo);
    }

    public Produk() {
    }

    protected Produk(Parcel in) {
        this.ID = in.readString();
        this.namaProduk = in.readString();
        this.hargaProduk = in.readString();
        this.stokProduk = in.readString();
        this.imgProduk = in.readString();
        this.deskripsi = in.readString();
        this.id_user = in.readString();
        this.nama = in.readString();
        this.no_tlp = in.readString();
        this.alamat = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<Produk> CREATOR = new Parcelable.Creator<Produk>() {
        @Override
        public Produk createFromParcel(Parcel source) {
            return new Produk(source);
        }

        @Override
        public Produk[] newArray(int size) {
            return new Produk[size];
        }
    };
}
