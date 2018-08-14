package com.iak.ali.mycrud;

import android.Manifest;
import android.app.AutomaticZenRule;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProdukWithImage extends AppCompatActivity {

    private static final String[] PERMISSION_READ =
            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    ImageView Gambar;
    LinearLayout linearLayout;
    EditText Nama, Harga, Stok, Deskripsi;
    Button Submit;
    PermissionsChecker checker;
    String imagePath,id;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produk_with_image);
        checker = new PermissionsChecker(this);

        id = getIntent().getStringExtra("id_user");
        Gambar = (ImageView)findViewById(R.id.img_produk_detail);
        Nama = (EditText) findViewById(R.id.edt_nama_produk);
        Harga = (EditText) findViewById(R.id.edt_harga_produk);
        Stok = (EditText)findViewById(R.id.edt_stok_produk);
        Submit = (Button)findViewById(R.id.btn_tambah);
        Deskripsi = (EditText) findViewById(R.id.edt_deskripsi);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

        Gambar.setOnClickListener(new View.OnClickListener() {
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

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Nama.getText().toString())){
                    Nama.setError("Nama tidak boleh kosong");
                }else if(TextUtils.isEmpty(Harga.getText().toString())){
                    Harga.setError("Harga tidak boleh kosong");
                }else if(TextUtils.isEmpty(Stok.getText().toString())){
                    Stok.setError("Stok tidak boleh kosong atau kurang dari satu");
                }else if(TextUtils.isEmpty(Deskripsi.getText().toString())){
                    Deskripsi.setError("deskripsi tidak boleh kosong ");
                }else if(Gambar.getDrawable() == null) {
                    Snackbar snackbar = Snackbar.make(linearLayout,"Anda belum memasukkan gambar",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else {
                    simpan();
                }
            }
        });

    }

    private void simpan() {
        File file = new File(imagePath);
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part gambar = MultipartBody.Part.createFormData("upload", file.getName(), requestBody);
        RequestBody nama_produk = RequestBody.create(MediaType.parse("text/plain"),Nama.getText().toString());
        RequestBody harga = RequestBody.create(MediaType.parse("text/plain"),Harga.getText().toString());
        RequestBody stok = RequestBody.create(MediaType.parse("text/plain"),Stok.getText().toString());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"),Deskripsi.getText().toString());
        RequestBody id_user = RequestBody.create(MediaType.parse("text/plain"),id);

        Call<ResponseBody> call = apiInterface.upload(gambar,nama_produk,harga,stok,deskripsi,id_user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(AddProdukWithImage.this, "cek database/file", Toast.LENGTH_SHORT).show();
                clear();
                Intent intent = new Intent(AddProdukWithImage.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddProdukWithImage.this, "Cek koneksi internet anda!", Toast.LENGTH_SHORT).show();
            }
        });
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

                    Picasso.with(context).load(new File(imagePath)).into(Gambar);
                    cursor.close();
                } else {
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
    private void clear() {
        Nama.setText("");
        Harga.setText("");
        Stok.setText("");
        Deskripsi.setText("");
    }
}
