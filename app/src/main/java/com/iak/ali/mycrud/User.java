
package com.iak.ali.mycrud;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("alamat")
    private String mAlamat;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("nama")
    private String mNama;
    @SerializedName("no_tlp")
    private String mNoTlp;
    @SerializedName("photo")
    private String mPhoto;

    protected User(Parcel in) {
        mAlamat = in.readString();
        mEmail = in.readString();
        mIdUser = in.readString();
        mNama = in.readString();
        mNoTlp = in.readString();
        mPhoto = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAlamat() {
        return mAlamat;
    }

    public void setAlamat(String alamat) {
        mAlamat = alamat;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
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

    public String getNoTlp() {
        return mNoTlp;
    }

    public void setNoTlp(String noTlp) {
        mNoTlp = noTlp;
    }
    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAlamat);
        dest.writeString(mEmail);
        dest.writeString(mIdUser);
        dest.writeString(mNama);
        dest.writeString(mNoTlp);
        dest.writeString(mPhoto);
    }
}
