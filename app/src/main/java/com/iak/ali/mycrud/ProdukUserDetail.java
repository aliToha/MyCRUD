package com.iak.ali.mycrud;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukUserDetail extends AppCompatActivity {
    @BindView(R.id.hrg_produk_detail) TextView mHarga;
    @BindView(R.id.stok_produk_detail) TextView mStok;
    @BindView(R.id.nm_produk_detail) TextView mNama;
    @BindView(R.id.img_produk_detail) ImageView mImgProduk;
    @BindView(R.id.edt_deskripsi) TextView mDeskripsi;

    private Produk produk;

    //private static final String BASE_URL = "http://192.168.1.2/iak_API/image_produk/";
    private static final String BASE_URL = "http://gagalcoding.000webhostapp.com/penjualan/image_produk/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_user_detail);
        ButterKnife.bind(this);
        produk = (Produk) getIntent().getParcelableExtra("produk");

        Picasso.with(this).load(BASE_URL+produk.getImgProduk()).into(mImgProduk);
        Locale localeID = new Locale("id","ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String hargaID = produk.getHargaProduk();

        mHarga.setText(numberFormat.format(Double.parseDouble(hargaID)));
        mNama.setText(produk.getNamaProduk());
        mStok.setText(produk.getStokProduk());
        mDeskripsi.setText(produk.getDeskripsi());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.opt_delete:
                delete();
                break;
            case R.id.opt_edit:
                Intent intent = new Intent(this,EditProduk.class);
                intent.putExtra("produk",produk);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin Mau dihapus ?");
        builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<Value> call=apiInterface.hapus(produk.getID());
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        String value = response.body().getValue();
                        String message = response.body().getMessage();
                        if(value.equals("1")){
                            startActivity(new Intent(ProdukUserDetail.this,MainActivity.class));
                            finish();
                            Toast.makeText(ProdukUserDetail.this,message, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ProdukUserDetail.this,message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
