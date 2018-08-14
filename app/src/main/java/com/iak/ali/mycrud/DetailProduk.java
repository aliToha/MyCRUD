package com.iak.ali.mycrud;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class DetailProduk extends AppCompatActivity {

    @BindView(R.id.hrg_produk_detail) TextView mHarga;
    @BindView(R.id.stok_produk_detail) TextView mStok;
    @BindView(R.id.nm_produk_detail) TextView mNama;
    @BindView(R.id.img_produk_detail) ImageView mImgProduk;
    @BindView(R.id.edt_deskripsi) TextView mDeskripsi;
    @BindView(R.id.edt_penjual)TextView mPenjual;
    @BindView(R.id.edt_alamat)TextView mAlamat;
    @BindView(R.id.img_user)CircleImageView imgUser;
    @BindView(R.id.fa_contact)FloatingActionButton floatingActionButton;

    private Produk produk;
    private static final String[] PERMISSION_CALL =
            new String[]{Manifest.permission.CALL_PHONE};
    PermissionsChecker checker;

    //private static final String BASE_URL = "http://192.168.1.2/iak_API/image_produk/";
    private static final String BASE_URL = "http://gagalcoding.000webhostapp.com/penjualan/image_produk/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        ButterKnife.bind(this);
        produk = (Produk) getIntent().getParcelableExtra("produk");
        checker = new PermissionsChecker(this);
        //ubah format harga rupiah
        Locale localeID = new Locale("id","ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String hargaID = produk.getHargaProduk();

        mHarga.setText(numberFormat.format(Double.parseDouble(hargaID)));
        mNama.setText(produk.getNamaProduk());
        mStok.setText(produk.getStokProduk());
        mDeskripsi.setText(produk.getDeskripsi());
        mPenjual.setText(produk.getNama());
        mAlamat.setText(produk.getAlamat());
        Picasso.with(this).load(BASE_URL+produk.getImgProduk()).into(mImgProduk);
        Picasso.with(this).load(produk.getPhoto()).into(imgUser);


        final String notlp = produk.getNo_tlp();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.lacksPermissions(PERMISSION_CALL)){
                    startPermissionActivity(PERMISSION_CALL);
                }else{
                    Intent i = new Intent();
                    i.setData(Uri.parse("tel:"+notlp));
                    i.setAction(Intent.ACTION_CALL);

                    Intent ii = Intent.createChooser(i,"silahkan ambil gambar");
                    startActivityForResult(ii,404);
                }


              //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+notlp));
               //startActivity(intent);
            }
        });
    }
    private void startPermissionActivity(String[] permissionRead) {
        PermissionsActivity.startActivityForResult(this,0,permissionRead);
    }

}
