package com.iak.ali.mycrud;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProduk extends AppCompatActivity {
    @BindView(R.id.img_produk_edit)ImageView mImageProduk;
    @BindView(R.id.edt_nama_produk)TextView mNamaProduk;
    @BindView(R.id.edt_harga_produk)TextView mHrgProduk;
    @BindView(R.id.edt_stok_produk)TextView mStokproduk;
    @BindView(R.id.edt_deskripsi)TextView mDeskripsi;
    @BindView(R.id.btn_tambah)Button mSubmit;
    @BindView(R.id.linearLayout)LinearLayout linearLayout;

    private Produk produk;
    //private static final String BASE_URL = "http://192.168.1.2/iak_API/image_produk/";
    private static final String BASE_URL = "https://gagalcoding.000webhostapp.com/penjualan/image_produk/";

    Context context;
    PermissionsChecker checker;
    String imagePath,id,idUser,nama,stokProduk,harga,deskripsi;
    private static final String[] PERMISSION_READ =
            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produk);
        checker = new PermissionsChecker(this);
        ButterKnife.bind(this);
        produk = getIntent().getParcelableExtra("produk");
        Picasso.with(this).load(BASE_URL+produk.getImgProduk()).into(mImageProduk);
        mNamaProduk.setText(produk.getNamaProduk());
        mHrgProduk.setText(produk.getHargaProduk());
        mStokproduk.setText(produk.getStokProduk());
        mDeskripsi.setText(produk.getDeskripsi());
        id = produk.getID();
        idUser = produk.getId_user();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mNamaProduk.getText().toString())){
                    mNamaProduk.setError("Nama tidak boleh kosong");
                }else if(TextUtils.isEmpty(mHrgProduk.getText().toString())){
                    mHrgProduk.setError("Harga tidak boleh kosong");
                }else if(TextUtils.isEmpty(mStokproduk.getText().toString())){
                    mStokproduk.setError("Stok tidak boleh kosong atau kurang dari satu");
                }else if(TextUtils.isEmpty(mDeskripsi.getText().toString())){
                    mDeskripsi.setError("deskripsi tidak boleh kosong ");
                }else if(mImageProduk.getDrawable() == null) {
                    Snackbar snackbar = Snackbar.make(linearLayout,"Anda belum memasukkan gambar",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else {
                    simpan();
                }
            }
        });
        mImageProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.lacksPermissions(PERMISSION_READ)){
                    startPermissionActivity(PERMISSION_READ);
                }else{
                    Intent i = new Intent();
                    i.setType("image/*");
                    i.setAction(Intent.ACTION_PICK);

                    Intent ii = Intent.createChooser(i,"silahkan ambil gambar");
                    startActivityForResult(ii,404);
                }
            }
        });
    }
    private void simpan() {
        File  file = new File(imagePath);
        if(file == null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part gambar = MultipartBody.Part.createFormData("upload", file.getName(), requestBody);

            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            RequestBody nama_produk = RequestBody.create(MediaType.parse("text/plain"), mNamaProduk.getText().toString());
            RequestBody harga = RequestBody.create(MediaType.parse("text/plain"), mHrgProduk.getText().toString());
            RequestBody stok = RequestBody.create(MediaType.parse("text/plain"), mStokproduk.getText().toString());
            RequestBody ID = RequestBody.create(MediaType.parse("text/plain"), id);
            RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), mDeskripsi.getText().toString());
            RequestBody iDuser = RequestBody.create(MediaType.parse("text/plain"), idUser);

            Call<ResponseBody> call = apiInterface.edit(gambar, nama_produk, harga, stok, ID, deskripsi, iDuser);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(EditProduk.this, "cek database/file", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditProduk.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(EditProduk.this, "Cek koneksi internet anda!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            nama = mNamaProduk.getText().toString().trim();
            harga = mHrgProduk.getText().toString();
            stokProduk = mStokproduk.getText().toString();
            deskripsi = mDeskripsi.getText().toString();
            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<Value> call = apiInterface.editWithoutImage(nama,harga,stokProduk,id,deskripsi,idUser);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    Toast.makeText(EditProduk.this, "cek database/file", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditProduk.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    Toast.makeText(EditProduk.this, "Cek koneksi internet anda!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && requestCode == 404) {
                Uri pilihGambar = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver()
                        .query(pilihGambar, filePath, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int index = cursor.getColumnIndex(filePath[0]);
                    imagePath = cursor.getString(index);

                    Picasso.with(context).load(new File(imagePath)).into(mImageProduk);
                    cursor.close();
                    mImageProduk.setVisibility(View.VISIBLE);
                } else {
                    mImageProduk.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "anda belum memilih gambar", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            Toast.makeText(this, "Terjadi kesalahan!", Toast.LENGTH_SHORT).show();
        }
    }
    private void startPermissionActivity(String[] permissionRead) {
        PermissionsActivity.startActivityForResult(this,0,permissionRead);
    }
}
