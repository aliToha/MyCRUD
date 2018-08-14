
package com.iak.ali.mycrud;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProdukUser implements Parcelable{

    @SerializedName("gmbr_produk")
    private String mGmbrProduk;
    @SerializedName("hrg_produk")
    private String mHrgProduk;
    @SerializedName("id")
    private String mId;
    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("nama")
    private String mNama;
    @SerializedName("nm_produk")
    private String mNmProduk;
    @SerializedName("no_tlp")
    private String mNoTlp;
    @SerializedName("stok")
    private String mStok;

    protected ProdukUser(Parcel in) {
        mGmbrProduk = in.readString();
        mHrgProduk = in.readString();
        mId = in.readString();
        mIdUser = in.readString();
        mNama = in.readString();
        mNmProduk = in.readString();
        mNoTlp = in.readString();
        mStok = in.readString();
    }

    public static final Creator<ProdukUser> CREATOR = new Creator<ProdukUser>() {
        @Override
        public ProdukUser createFromParcel(Parcel in) {
            return new ProdukUser(in);
        }

        @Override
        public ProdukUser[] newArray(int size) {
            return new ProdukUser[size];
        }
    };

    public String getGmbrProduk() {
        return mGmbrProduk;
    }

    public void setGmbrProduk(String gmbrProduk) {
        mGmbrProduk = gmbrProduk;
    }

    public String getHrgProduk() {
        return mHrgProduk;
    }

    public void setHrgProduk(String hrgProduk) {
        mHrgProduk = hrgProduk;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getIdUser() {
        return mIdUser;
    }

    public void setIdUser(String idUser) {
        mIdUser = idUser;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        mNama = nama;
    }

    public String getNmProduk() {
        return mNmProduk;
    }

    public void setNmProduk(String nmProduk) {
        mNmProduk = nmProduk;
    }

    public String getNoTlp() {
        return mNoTlp;
    }

    public void setNoTlp(String noTlp) {
        mNoTlp = noTlp;
    }

    public String getStok() {
        return mStok;
    }

    public void setStok(String stok) {
        mStok = stok;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGmbrProduk);
        dest.writeString(mHrgProduk);
        dest.writeString(mId);
        dest.writeString(mIdUser);
        dest.writeString(mNama);
        dest.writeString(mNmProduk);
        dest.writeString(mNoTlp);
        dest.writeString(mStok);
    }
}
