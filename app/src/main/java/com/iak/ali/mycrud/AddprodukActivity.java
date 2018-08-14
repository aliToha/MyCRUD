package com.iak.ali.mycrud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddprodukActivity extends AppCompatActivity {
    EditText nm_produk,hrg_produk,stok,deskripsi;
    Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduk);
        nm_produk = (EditText)findViewById(R.id.edt_nama_produk);
        hrg_produk = (EditText)findViewById(R.id.edt_harga_produk);
        stok = (EditText)findViewById(R.id.edt_stok_produk);
        deskripsi = (EditText)findViewById(R.id.edt_deskripsi_produk);
        Submit = (Button)findViewById(R.id.btn_tambah);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nm_produk.getText().toString())){
                    nm_produk.setError("Nama tidak boleh kosong");
                }else if(TextUtils.isEmpty(hrg_produk.getText().toString())){
                    hrg_produk.setError("Harga tidak boleh kosong");
                }else if(TextUtils.isEmpty(stok.getText().toString())){
                    stok.setError("Stok tidak boleh kosong atau kurang dari satu");
                }else if(TextUtils.isEmpty(deskripsi.getText().toString())){
                    deskripsi.setError("deskripsi tidak boleh kosong ");
                }else{
                    simpan();
                }
            }
        });
    }

    private void simpan() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<Value> call = apiInterface.addProduk(nm_produk.getText().toString().trim(),
                hrg_produk.getText().toString(),
                stok.getText().toString(), deskripsi.getText().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if(value.equals("1")){
                    Toast.makeText(AddprodukActivity.this,message, Toast.LENGTH_SHORT).show();
                    clear();
                    Intent intent = new Intent(AddprodukActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(AddprodukActivity.this,message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(AddprodukActivity.this, "Cek koneksi internet anda!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clear() {
        nm_produk.setText("");
        hrg_produk.setText("");
        stok.setText("");
        deskripsi.setText("");
    }
}
